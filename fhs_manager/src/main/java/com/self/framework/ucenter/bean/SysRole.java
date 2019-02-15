package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @des 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysRole extends BaseBean {

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
    private Integer status = 0;//状态

    @Column(name = "is_delete")
    private Integer is_delete = 0;//是否被删除

//    @ManyToMany(mappedBy = "userRoles", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @NoSpecificationQuery
//    private List<SysUser> users;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "role_menu", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", referencedColumnName = "id" ) }) //被控方表字段名
    @NoSpecificationQuery
    private List<SysMenuResource> sysMenuResources;
}
