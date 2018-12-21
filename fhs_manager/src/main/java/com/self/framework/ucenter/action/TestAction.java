package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.ucenter.bean.TestBean;
import com.self.framework.ucenter.service.TestService;
import com.self.framework.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("test")
    @ResponseBody
    public Map<String,Object> test0(HttpServletRequest request){
        TestBean bean = new TestBean();
        bean.setDes("bbbb");
        testService.add(bean);
        return new HashMap<String, Object>(){{
            put("result","ok");
        }};
    }

    public static void main(String[] args) {
        TestBean bean = new TestBean();
        bean.setDes("bbbb");
        try {
            System.out.println(ReflectUtil.reflectObjObtainFileMethod(bean,"des").invoke(bean));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

