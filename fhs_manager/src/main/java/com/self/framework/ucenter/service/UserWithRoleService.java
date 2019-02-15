package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;
import com.self.framework.ucenter.bean.SysUserWithRole;

import java.util.List;

/**
 *
 */
public interface UserWithRoleService extends BaseService<SysUserWithRole> {
    Integer addAll(List<SysUserWithRole> sysUserWithRolses, String userId);
}
