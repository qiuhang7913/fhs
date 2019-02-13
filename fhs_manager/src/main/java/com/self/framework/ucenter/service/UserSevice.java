package com.self.framework.ucenter.service;

import com.self.framework.base.BaseService;
import com.self.framework.ucenter.bean.SysUser;

import java.util.List;

/**
 * @des 用户管理 ifa
 * @author qiuhang
 * @version v1.0
 */
public interface UserSevice extends BaseService<SysUser> {
    Integer USER_IS_DELTE_Y = 1;//删除
    Integer USER_IS_DELTE_N = 0;//非删除

    /**
     * 登陆方法
     * @param loginName
     * @return
     */
    SysUser loadUserByLoginName(String loginName);

    /**
     * 用户的删除
     * @param ids
     * @return
     */
    boolean deleteUser(List<String> ids);
}
