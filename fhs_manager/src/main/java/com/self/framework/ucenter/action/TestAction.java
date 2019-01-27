package com.self.framework.ucenter.action;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseAction;
import com.self.framework.base.BaseBean;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.TestBean;
import com.self.framework.ucenter.service.TestService;
import com.self.framework.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("test")
public class TestAction extends BaseAction<TestBean> {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        String testStr = "{\"icon\":\"111\",\"name\":\"123\",\"sort\":\"2\",\"type\":\"0\",\"url\":\"#\", \"createTime\":\"wwww\"}";
        BaseBean parse = JSON.parseObject(testStr, BaseBean.class);
        System.out.println(parse.getCreateTime());
    }
}

