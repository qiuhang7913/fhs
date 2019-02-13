package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.TestBean;
import com.self.framework.ucenter.dao.TestDao;
import com.self.framework.ucenter.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends BaseServiceImpl<TestBean> implements TestService {

    @Autowired
    private TestDao testDao;


}
