package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.service.RoleService;
import com.self.framework.ucenter.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleAction extends BaseAction<SysRole> {

    @Autowired
    private RoleService roleService;

}
