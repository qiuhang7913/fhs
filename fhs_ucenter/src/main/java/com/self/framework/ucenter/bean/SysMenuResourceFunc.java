package com.self.framework.ucenter.bean;

import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @des 菜单资源方法实体
 */
@Data
@Entity(name = "menu_func")
public class SysMenuResourceFunc extends BaseBean {

    /**
     *  菜单id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "menu_id")
    private String menuId;//菜单资源id

    @Column(name = "func_type")
    private Integer funcType;//方法类型(0:增加 1:修改 2:删除 3:查询)

    @Column(name = "func_name")
    private String funcName;//方法名

    @Column(name = "func_des")
    private Integer funcDes;//方法备注描述

}
