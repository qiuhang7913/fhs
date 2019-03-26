package com.self.framework.building.action;

import com.self.framework.annotation.SysLog;
import com.self.framework.base.BaseAction;
import com.self.framework.building.bean.BuildingConfigBean;
import com.self.framework.building.service.BuildingConfigService;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysLoginAuthConfig;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/building/config")
@SysLog
public class BuildingConfigAction extends BaseAction<BuildingConfigBean> {

    @Autowired
    private BuildingConfigService buildingConfigService;

    @RequestMapping(value = "findOneByCode", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('super','/building/config:find')")
    @ResponseBody
    public HttpResult<BuildingConfigBean> findOneByCode(@RequestParam(name = "code") String code){
        BuildingConfigBean one = buildingConfigService.findOne(BuildingConfigBean.builder().configCode(code).build());
        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            return HttpResult.errorOtherDataResult(BuildingConfigBean.builder().build());
        }
        return HttpResult.okOtherDataResult(one);
    }
}
