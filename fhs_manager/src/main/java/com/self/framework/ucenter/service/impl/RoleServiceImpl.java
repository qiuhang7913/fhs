package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.dao.RoleDao;
import com.self.framework.ucenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole> implements RoleService {

    @Autowired
    public RoleDao roleDao;

    public HttpResult<Map> obtainCurrRoleAllRes(){
        return HttpResult.okResult();
    }
}
