package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @des 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
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
    @NotNull
    private String name;//资源名

    @Column(name = "url")
    @NotNull
    private String url;//资源路径

    @Column(name = "type")
    @NotNull
    @Min(value = 0, message = "类型最小只能0")
    private Integer type;//资源类型

    @Column(name = "parent_id")
    @NotNull
    private String parentId;//父资源类型

    @Column(name = "sort")
    @NotNull
    @Min(value = 0, message = "排序值最小只能0")
    private Integer sort;//资源排序

    @Column(name = "name_space")
    @NotNull
    private String nameSpace;//资源空间

    @Column(name = "status")
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态最小只能0")
    private Integer status;//状态

    @OneToMany(mappedBy = "menuId", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @NoSpecificationQuery
    private List<SysMenuResourceFunc> sysMenuResourceFuncs;


//    @ManyToMany(mappedBy = "sysMenuResources", fetch = FetchType.LAZY)
//    @NoSpecificationQuery
//    private List<SysRole> userRoles;

    @Transient
    private List<Map> sysMenuResFuncIds;

    @Transient
    private List<SysMenuResource> children;
}
