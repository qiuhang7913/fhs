package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.bean.SysUserWithRole;
import com.self.framework.ucenter.service.UserSevice;
import com.self.framework.ucenter.service.UserWithRoleService;
import com.self.framework.utils.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/sys/user")
public class UserAction extends BaseAction<SysUser> {

    @Autowired
    private UserSevice userSevice;

    @Autowired
    private UserWithRoleService userWithRoleService;

    @RequestMapping(value = "obtainUserRoleIds/{userId}", method = {RequestMethod.GET})
    @ResponseBody
    public HttpResult<List> obtainUserRoleIds(@PathVariable(value = "userId" )String userId){
        List<SysUserWithRole> sysUserWithRoles = userWithRoleService.queryList(SysUserWithRole.builder().userId(userId).build());
        List<String> userRoleIds = sysUserWithRoles.stream().map(SysUserWithRole::getRoleId).collect(Collectors.toList());
        return HttpResult.okOtherDataResult(userRoleIds);
    }

    @RequestMapping(value = "saveUserRoleRelation/{userId}", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> saveUserRoleRelation(@PathVariable(value = "userId" )String userId, @RequestBody List<String> roleIds){
        List<SysUserWithRole> sysUserWithRoles = new ArrayList<>();
        roleIds.forEach(roleId ->{
            SysUserWithRole sysUserWithRole = new SysUserWithRole();
            sysUserWithRole.setUserId(userId);
            sysUserWithRole.setRoleId(roleId);
            sysUserWithRole.setCreateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
            sysUserWithRole.setCreateUser(getSessionUserInfo().getUsername());
            sysUserWithRoles.add(sysUserWithRole);
        });
        userWithRoleService.addAll(sysUserWithRoles, userId);
        return HttpResult.okResult();
    }

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
