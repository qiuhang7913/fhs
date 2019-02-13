package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/user")
public class UserAction extends BaseAction<SysUser> {

    @Autowired
    private UserSevice userSevice;

    @Override
    public HttpResult<SysUser> obtainOne(@PathVariable(value = "id" )String id) {
        HttpResult<SysUser> sysUserHttpResult = super.obtainOne(id);
        SysUser sysUser = null;
        if(sysUserHttpResult.getCode() == HttpCodeConstant.HTTP_OK_CODE){
            sysUser = sysUserHttpResult.getResult();
            sysUser.setPassword(BusinessCommonConstamt.DEFALUT_USER_PASSWORD);
            return HttpResult.okOtherDataResult(sysUser);
        }
        return HttpResult.errorOtherDataResult(sysUser);
    }

    @Override
    public HttpResult<Map> delete(@RequestBody List<String> ids) {
        return userSevice.deleteUser(ids) ? HttpResult.okResult() : HttpResult.errorResult();
    }
}
