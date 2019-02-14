package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @des: 资源菜单于角色多对多关系bean
 * @author qiuhang
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "role_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuResourceWithRole extends BaseBean {
    /**
     *  主键id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    /**
     * 角色id
     */
    @Column(name = "role_id", nullable = false, length = 64)
    private String roleId;

    /**
     * 菜单id
     */
    @Column(name = "menu_id", nullable = false, length = 64)
    private String menuId;

    /**
     * 动作方法
     */
    @Column(name = "func_ids", nullable = false, length = 256)
    private String funcIds;
}
