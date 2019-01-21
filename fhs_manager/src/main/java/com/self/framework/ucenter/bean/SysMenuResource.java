package com.self.framework.ucenter.bean;

import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @des 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menu")
public class SysMenuResource extends BaseBean {
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
    private Integer status;//状态

    @OneToMany(mappedBy = "menuId", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @NoSpecificationQuery
    private List<SysMenuResourceFunc> sysMenuResourceFuncs;


    @ManyToMany(mappedBy = "sysMenuResources", fetch = FetchType.LAZY)
    @NoSpecificationQuery
    private List<SysRole> userRoles;
}
