package com.self.framework.ucenter.bean;

import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @des 用户实体
 */
@Data
@Entity(name = "role")
public class SysRole extends BaseBean {

    @Builder
    public SysRole(String createTime, String createUser, String updateTime, String updateUser, Map<String, String> between, String roleName, String roleDes, String status, Integer is_delete) {
        super(createTime, createUser, updateTime, updateUser, between);
        this.roleName = roleName;
        this.roleDes = roleDes;
        this.status = status;
        this.is_delete = is_delete;
    }

    /**
     *  用户id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "role_name")
    private String roleName;//角色名

    @Column(name = "role_des")
    private String roleDes;//角色描述

    @Column(name = "status")
    private String status;//状态

    @Column(name = "is_delete")
    private Integer is_delete;//是否被删除

    @ManyToMany(mappedBy = "userRoles", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @NoSpecificationQuery
    private List<SysUser> users;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "role_menu", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "id" ) }) //被控方表字段名
    @NoSpecificationQuery
    private List<SysMenuResource> sysMenuResources;
}
