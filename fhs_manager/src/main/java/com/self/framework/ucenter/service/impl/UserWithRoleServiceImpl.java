package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;
import com.self.framework.ucenter.bean.SysUserWithRole;
import com.self.framework.ucenter.dao.UserWithRoleDao;
import com.self.framework.ucenter.service.MenuWithRoleService;
import com.self.framework.ucenter.service.UserWithRoleService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWithRoleServiceImpl extends BaseServiceImpl<SysUserWithRole> implements UserWithRoleService {

    @Autowired
    private UserWithRoleDao dao;

    @Override
    public Integer addAll(List<SysUserWithRole> sysUserWithRoles, String userId) {
        List<SysUserWithRole> all = dao.findAll(Example.of(SysUserWithRole.builder().userId(userId).build()));
        dao.deleteAll(all);
        return ObjectCheckUtil.checkIsNullOrEmpty(dao.saveAll(sysUserWithRoles)) ? 0 : 1 ;
    }
}
