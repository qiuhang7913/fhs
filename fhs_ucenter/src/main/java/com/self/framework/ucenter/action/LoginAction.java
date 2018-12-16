package com.self.framework.ucenter.action;

import com.self.framework.ucenter.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginAction {

    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @RequestMapping(value = "/login")
    public String goLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/userLogin")
    public String userLogin(HttpServletRequest  request) {
        SysUser sysUser = SysUser.builder().loginName("qiuhang").password("123456").build();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken("qiuhang", "123456");

        //使用SpringSecurity拦截登陆请求 进行认证和授权
        try {
            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:index";
    }
}
