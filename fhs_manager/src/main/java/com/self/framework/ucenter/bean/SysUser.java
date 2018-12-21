package com.self.framework.ucenter.bean;

import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import com.self.framework.constant.BusinessCommonConstamt;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @des 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "user")
public class SysUser extends BaseBean implements UserDetails {

    /**
     *  用户id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "real_name")
    private String realName;//真实姓名

    @Column(name = "login_name")
    private String loginName;//登录名

    @Column(name = "password")
    private String password;//密码

    @Column(name = "sex")
    private Integer sex;//性别

    @Column(name = "birthday")
    private Integer birthday;//生日

    @Column(name = "address")
    private String address;//详细地址

    @Column(name = "province")
    private String province;//详细地址

    @Column(name = "city")
    private String city;//城市

    @Column(name = "area")
    private String area;//区

    @Column(name = "status")
    private Integer status;//用户状态

    @Column(name = "type")
    private Integer type;//用户类型

    @Column(name = "is_delete")
    private Integer is_delete;//是否被删除

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id" ) }) //被控方表字段名
    @NoSpecificationQuery
    private List<SysRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        Set<String> authResources = new HashSet<>();
        List<SysRole> userRoleBeans = this.getUserRoles();//用户权限
        //权限资源
        userRoleBeans.stream().map(SysRole::getSysMenuResources).forEach(sysMenuResources -> sysMenuResources.forEach(sysMenuResource -> {
            String name = sysMenuResource.getName();//资源名称
            List<SysMenuResourceFunc> sysMenuResourceFuncs = sysMenuResource.getSysMenuResourceFuncs();
            sysMenuResourceFuncs.forEach(sysMenuResourceFunc -> {
                String authFlag = name + ":";
                Integer funcType = sysMenuResourceFunc.getFuncType();
                if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG;
                }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_FLAG;
                }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_FLAG;
                }else{
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG;
                }
                authResources.add(authFlag);
            });
        }));
        authResources.forEach( authResource -> {
            auths.add(new SimpleGrantedAuthority(authResource));
        });

        return auths;
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
