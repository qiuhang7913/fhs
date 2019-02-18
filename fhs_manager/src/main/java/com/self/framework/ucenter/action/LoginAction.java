package com.self.framework.ucenter.action;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.self.framework.constant.HttpSessionAttrConstant;
import com.self.framework.ucenter.from.LoginForm;
import com.self.framework.ucenter.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class LoginAction {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping(value = "/")
    public String goIndex(HttpServletRequest  request){
        return "redirect:index";
    }

    @RequestMapping(value = "/login")
    public String goLoginPage(HttpServletRequest  request){
        logger.info("进入login" + request.getParameter("error"));
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST})
    public ModelAndView userLogin(@Valid LoginForm loginForm, HttpServletRequest request) {
        ModelAndView modelAttribute = new ModelAndView();
        //验证码校验
        String s = request.getSession().getAttribute(HttpSessionAttrConstant.HTTP_VERIFICATION_CODE_NAME).toString();
        if (!s.equals(loginForm.getVerificationCode())) {
            modelAttribute.setViewName("redirect:login");
            modelAttribute.addObject("error","2");
            return modelAttribute;
        }

        //使用SpringSecurity拦截登陆请求 进行认证和授权
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginForm.getLoginName(), loginForm.getPassword());
            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            modelAttribute.setViewName("redirect:index");
        }catch (Exception e){
            logger.error("登陆失败,失败原因可能为{},请求参数为{}",e.getMessage(), loginForm.asJson());
            modelAttribute.setViewName("redirect:login");
            modelAttribute.addObject("error","0");
        }
        return modelAttribute;
    }

    @RequestMapping("/captcha.jpg")
    public void applyCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        //保存到session
        request.getSession().setAttribute(HttpSessionAttrConstant.HTTP_VERIFICATION_CODE_NAME, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

    }
}
