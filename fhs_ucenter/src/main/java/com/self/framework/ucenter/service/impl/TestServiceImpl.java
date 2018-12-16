package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.ucenter.bean.TestBean;
import com.self.framework.ucenter.dao.TestDao;
import com.self.framework.ucenter.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl extends BaseServiceImpl<TestBean> implements TestService {

    @Resource
    private TestDao testDao;

}
