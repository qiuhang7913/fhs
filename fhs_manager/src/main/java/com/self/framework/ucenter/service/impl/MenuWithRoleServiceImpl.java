package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;
import com.self.framework.ucenter.dao.MenuWithRoleDao;
import com.self.framework.ucenter.service.MenuWithRoleService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuWithRoleServiceImpl extends BaseServiceImpl<SysMenuResourceWithRole> implements MenuWithRoleService {

    @Autowired
    private MenuWithRoleDao dao;

    @Override
    public Integer addAll(List<SysMenuResourceWithRole> sysMenuResourceWithRoles) {
        dao.deleteAll(sysMenuResourceWithRoles);
        return ObjectCheckUtil.checkIsNullOrEmpty(dao.saveAll(sysMenuResourceWithRoles)) ? 0 : 1 ;
    }
}
