package com.self.framework.ucenter.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.self.framework.base.BaseAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.SysMenuResourceWithRole;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.service.MenuWithRoleService;
import com.self.framework.ucenter.service.RoleService;
import com.self.framework.ucenter.service.UserSevice;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleAction extends BaseAction<SysRole> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuWithRoleService menuWithRoleService;

    /**
     * 获取当前角色下的某一个角色菜单下的菜单资源动作
     * @return
     */
    @RequestMapping(value = "obtainCurrRoleMenuFuncs", method = {RequestMethod.GET})
    @ResponseBody
    public HttpResult<List> obtainCurrRoleMenuFuncs(@RequestParam( value = "roleId") String roleId,
                                                   @RequestParam(value = "menuId") String menuId){
        SysMenuResourceWithRole one = menuWithRoleService.findOne(SysMenuResourceWithRole.builder().roleId(roleId).menuId(menuId).build());
        if (!ObjectCheckUtil.checkIsNullOrEmpty(one)){
           return HttpResult.okOtherDataResult(JSON.parseArray(one.getFuncIds()));
        }
        return HttpResult.errorOtherDataResult(new JSONArray());
    }

    @RequestMapping(value = "addOrUpdateNew", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> addOrUpdateNew(@RequestBody SysRole sysRole) {
        List<SysMenuResource> sysMenuResources = sysRole.getSysMenuResources();
        //1:先入库角色
        sysRole.setSysMenuResources(new ArrayList<>());
        SysRole sysRoleNew = roleService.addOrUpdataReturn(sysRole);
        if (!ObjectCheckUtil.checkIsNullOrEmpty(sysRoleNew)) {
            //2:对应关系入库
            List<SysMenuResourceWithRole> sysMenuResourceWithRoles = new ArrayList<>();
            sysMenuResources.forEach(sysMenuResource -> {
                SysMenuResourceWithRole sysMenuResourceWithRole = new SysMenuResourceWithRole();
                sysMenuResourceWithRole.setMenuId(sysMenuResource.getId());
                sysMenuResourceWithRole.setRoleId(sysRoleNew.getId());
                sysMenuResourceWithRole.setFuncIds(JSON.toJSONString(sysMenuResource.getSysMenuResourceFuncs()));
                sysMenuResourceWithRoles.add(sysMenuResourceWithRole);
            });

            Integer rv = menuWithRoleService.addAll(sysMenuResourceWithRoles, sysRoleNew.getId());
            return rv > 0 ? HttpResult.okResult() : HttpResult.okResult();
        }
        return HttpResult.errorResult();
    }
}
