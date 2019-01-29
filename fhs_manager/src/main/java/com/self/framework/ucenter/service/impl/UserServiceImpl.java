package com.self.framework.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseBean;
import com.self.framework.base.BaseDao;
import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.dao.UserDao;
import com.self.framework.ucenter.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @des 用户管理 ifa 实现
 * @author qiuhang
 * @version v1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<SysUser> implements UserSevice {

    @Autowired
    private UserDao userDao;

    public SysUser loadUserByLoginName(String loginName) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(loginName);
        Example<SysUser> of = Example.of(sysUser);
        Optional<SysUser> one = userDao.findOne(of);
        try {
            return one.get();
        }catch (Exception e){
            return null;
        }
    }

}
