package com.self.framework.ucenter.service;

import com.self.framework.ucenter.bean.TestBean;
import com.self.framework.ucenter.dao.TestDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    public TestBean add(TestBean bean){
        TestBean testBean = testDao.saveAndFlush(bean);
        return testBean;
    }
}
