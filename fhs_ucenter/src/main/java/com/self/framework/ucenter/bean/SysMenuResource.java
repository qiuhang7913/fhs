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
@Entity(name = "menu")
public class SysMenuResource extends BaseBean {

    @Builder
    public SysMenuResource(String createTime, String createUser, String updateTime, String updateUser, Map<String, String> between, String icon, String name, String url, Integer type, String parentId, Integer sort, String status, List<SysMenuResourceFunc> sysMenuResourceFuncs) {
        super(createTime, createUser, updateTime, updateUser, between);
        this.icon = icon;
        this.name = name;
        this.url = url;
        this.type = type;
        this.parentId = parentId;
        this.sort = sort;
        this.status = status;
        //this.sysMenuResourceFuncs = sysMenuResourceFuncs;
    }

    /**
     *  菜单id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "icon")
    private String icon;//资源图标

    @Column(name = "name")
    private String name;//资源名

    @Column(name = "url")
    private String url;//资源路径

    @Column(name = "type")
    private Integer type;//资源类型

    @Column(name = "parent_id")
    private String parentId;//父资源类型

    @Column(name = "sort")
    private Integer sort;//资源排序

    @Column(name = "status")
    private String status;//状态

    @OneToMany(mappedBy = "menuId", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @NoSpecificationQuery
    private List<SysMenuResourceFunc> sysMenuResourceFuncs;


    @ManyToMany(mappedBy = "sysMenuResources", fetch = FetchType.LAZY)
    @NoSpecificationQuery
    private List<SysRole> userRoles;
}
