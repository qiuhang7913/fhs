package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.otherBean.TreeNode;
import com.self.framework.ucenter.bean.SysMenuResource;

import java.util.List;

public interface MenuService extends BaseService<SysMenuResource> {
    String MENU_TREENODES_CACHE_CODE = "sys:menu:treenodes";

    /**
     * 树形数据查询
     * @return
     */
    List<TreeNode> findMenuTreeData();
}
