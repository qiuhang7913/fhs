package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;


public interface MenuFuncService extends BaseService<SysMenuResourceFunc> {
    /**
     * 一键添加所有默认的动作
     * @param menuId
     * @return
     */
    Integer addAll(String menuId);
}
