package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;

import java.util.List;

/**
 *
 */
public interface MenuWithRoleService extends BaseService<SysMenuResourceWithRole> {
    Integer addAll(List<SysMenuResourceWithRole> sysMenuResourceWithRoles, String roleId);
}
