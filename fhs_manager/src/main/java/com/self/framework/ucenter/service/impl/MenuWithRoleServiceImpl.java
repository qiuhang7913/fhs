package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;
import com.self.framework.ucenter.dao.MenuWithRoleDao;
import com.self.framework.ucenter.service.MenuWithRoleService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuWithRoleServiceImpl extends BaseServiceImpl<SysMenuResourceWithRole> implements MenuWithRoleService {

    @Autowired
    private MenuWithRoleDao dao;

    @Override
    public Integer addAll(List<SysMenuResourceWithRole> sysMenuResourceWithRoles, String roleId) {
        List<SysMenuResourceWithRole> all = dao.findAll(Example.of(SysMenuResourceWithRole.builder().roleId(roleId).build()));
        dao.deleteAll(all);
        return ObjectCheckUtil.checkIsNullOrEmpty(dao.saveAll(sysMenuResourceWithRoles)) ? 0 : 1 ;
    }
}
