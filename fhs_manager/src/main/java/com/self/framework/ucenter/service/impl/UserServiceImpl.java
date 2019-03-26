package com.self.framework.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseBean;
import com.self.framework.base.BaseDao;
import com.self.framework.base.BaseServiceImpl;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.dao.UserDao;
import com.self.framework.ucenter.service.UserSevice;
import com.self.framework.utils.DateTool;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Override
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

    @Override
    public boolean deleteUser(List<String> ids){
        List<SysUser> sysUsers = new ArrayList<>();
        ids.forEach(id -> {
            SysUser sysUser = userDao.findById(id).get();
            sysUser.setIsDelete(USER_IS_DELTE_Y);
            sysUsers.add(sysUser);
        });

        List<SysUser> rvSysUsers = userDao.saveAll(sysUsers);
        return ObjectCheckUtil.checkIsNullOrEmpty(rvSysUsers);
    }

    @Override
    public Integer addOrUpdata(SysUser v) {
        SysUser sysUserSession = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        if (ObjectCheckUtil.checkIsNullOrEmpty(v.getId())){
            v.setPassword(encoder.encode(v.getPassword().trim()));
            v.setCreateUser(sysUserSession.getUsername());
            v.setCreateUser(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
        }else{
            SysUser sysUserOriginal = userDao.findById(v.getId()).get();
            v.setType(sysUserOriginal.getType());//不修改类型
            if (BusinessCommonConstamt.DEFALUT_USER_PASSWORD.equals(v.getPassword())){
                v.setPassword(sysUserOriginal.getPassword());//不修改密码
            }else{
                v.setPassword(encoder.encode(v.getPassword().trim()));
            }
            v.setCreateUser(sysUserOriginal.getCreateUser());
            v.setCreateTime(sysUserOriginal.getCreateTime());
            v.setUpdateUser(sysUserSession.getUsername());
            v.setUpdateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
        }
        return ObjectCheckUtil.checkIsNullOrEmpty(userDao.saveAndFlush(v)) ? 0 : 1;
    }
}
