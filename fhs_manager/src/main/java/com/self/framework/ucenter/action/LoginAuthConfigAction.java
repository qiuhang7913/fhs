package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysLoginAuthConfig;
import com.self.framework.ucenter.service.LoginAuthConfigService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sys/login_auth")
public class LoginAuthConfigAction extends BaseAction<SysLoginAuthConfig> {

    @Autowired
    private LoginAuthConfigService loginAuthConfigService;

    @RequestMapping(value = "findOneByCurrentSessionUser", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('super','/sys/loginAuthConfig:find')")
    @ResponseBody
    public HttpResult<SysLoginAuthConfig> findOneByCurrentSessionUser(){
        SysLoginAuthConfig one = loginAuthConfigService.findOne(SysLoginAuthConfig.builder().loginName(getSessionUserInfo().getUsername()).build());
        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            return HttpResult.aOtherResult(HttpCodeConstant.HTTP_FIND_ERROR_CODE,HttpCodeConstant.HTTP_FIND_ERROR_DESCRIBE,null);
        }else{
            return HttpResult.okOtherDataResult(one);
        }
    }
}
