package com.self.framework.building.api;

import com.self.framework.building.from.BuildingCustomerFrom;
import com.self.framework.building.vo.BuildingCustomerVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/building/customer")
public class BuildingCustomerApiService {

    @RequestMapping(method = RequestMethod.POST)
    public BuildingCustomerVo obtainOne(@RequestBody @Valid BuildingCustomerFrom buildingCustomerFrom){
        return BuildingCustomerVo.builder().name("qiuhang").build();
    }
}
