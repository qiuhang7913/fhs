package com.self.framework.building.action;

import com.self.framework.annotation.SysLog;
import com.self.framework.base.BaseAction;
import com.self.framework.building.bean.BuildingFloorBean;
import com.self.framework.building.service.BuildingFloorService;
import com.self.framework.building.vo.FloorComboxVo;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/building/floor")
@SysLog
public class BuildingFloorAction extends BaseAction<BuildingFloorBean> {

    @Autowired
    private BuildingFloorService buildingFloorService;

    @RequestMapping(value = "obatinFloorCombox", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('super','/building/floor:find')")
    @ResponseBody
    public HttpResult<List<FloorComboxVo>> obatinFloorCombox(){
       List<FloorComboxVo> dataList = buildingFloorService.findFloorIdAndCode();
       return HttpResult.okOtherDataResult(dataList);
    }
}
