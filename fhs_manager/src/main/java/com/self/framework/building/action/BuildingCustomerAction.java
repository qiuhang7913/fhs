package com.self.framework.building.action;

import com.self.framework.annotation.SysLog;
import com.self.framework.base.BaseAction;
import com.self.framework.building.bean.BuildingCustomerBean;
import com.self.framework.building.service.BuildingConfigService;
import com.self.framework.building.service.BuildingCustomerService;
import com.self.framework.http.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/building/customer")
@SysLog
public class BuildingCustomerAction extends BaseAction<BuildingCustomerBean> {

    @Autowired
    private BuildingCustomerService buildingCustomerService;

}
