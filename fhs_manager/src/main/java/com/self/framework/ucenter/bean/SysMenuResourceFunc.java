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
 * @des 菜单资源方法实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "menu_func")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
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
    private String funcDes;//方法备注描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getFuncType() {
        return funcType;
    }

    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncDes() {
        return funcDes;
    }

    public void setFuncDes(String funcDes) {
        this.funcDes = funcDes;
    }
}
