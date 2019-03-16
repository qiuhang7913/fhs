package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.SysLoginAuthConfig;
import com.self.framework.ucenter.dao.LoginAuthConfigDAO;
import com.self.framework.ucenter.service.LoginAuthConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthConfigServiceImpl extends BaseServiceImpl<SysLoginAuthConfig> implements LoginAuthConfigService {

    @Autowired
    private LoginAuthConfigDAO dao;
}
