package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysUser;

/**
 * @des 用户管理 ifa
 * @author qiuhang
 * @version v1.0
 */
public interface UserSevice extends BaseService<SysUser> {

    SysUser loadUserByLoginName(String loginName);
}
