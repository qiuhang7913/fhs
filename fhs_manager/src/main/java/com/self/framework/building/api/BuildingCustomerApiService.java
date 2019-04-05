package com.self.framework.building.api;

import com.alibaba.fastjson.JSON;
import com.self.easy_room.api.constant.CommonConstant;
import com.self.easy_room.api.constant.HttpApiRequestFuncConstant;
import com.self.easy_room.api.from.BuildingCustomerApiFrom;
import com.self.easy_room.api.vo.BuildingCustomerApiVo;
import com.self.framework.building.bean.BuildingCustomerBean;
import com.self.framework.building.service.BuildingCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/building/customer")
public class BuildingCustomerApiService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BuildingCustomerService buildingCustomerService;

    @RequestMapping(value = HttpApiRequestFuncConstant.OBTAIN_ONE, method = RequestMethod.POST)
    public BuildingCustomerApiVo obtainOne(@RequestBody @Valid BuildingCustomerApiFrom buildingCustomerFrom){
        BuildingCustomerBean buildingCustomerBean = new BuildingCustomerBean();
        BeanUtils.copyProperties(buildingCustomerFrom, buildingCustomerBean);
        try {
            buildingCustomerBean = buildingCustomerService.findOne(buildingCustomerBean);
            BuildingCustomerApiVo BuildingCustomerApiVo = new BuildingCustomerApiVo();
            BeanUtils.copyProperties(buildingCustomerBean, BuildingCustomerApiVo);
            return BuildingCustomerApiVo;
        }catch (Exception e){
            logger.error("数据查询异常,请求参数为{}", JSON.toJSONString(buildingCustomerFrom));
            BuildingCustomerApiVo errorVo = new BuildingCustomerApiVo();
            errorVo.setErrorCode(CommonConstant.HTTP_FIND_ERROR); errorVo.setErrorMsg(CommonConstant.HTTP_FIND_ERROR_DES);
            return errorVo;
        }
    }
}
