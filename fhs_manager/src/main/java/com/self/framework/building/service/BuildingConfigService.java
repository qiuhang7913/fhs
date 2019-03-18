package com.self.framework.building.service;

import com.self.framework.base.BaseService;
import com.self.framework.building.bean.BuildingConfigBean;
import com.self.framework.building.bean.BuildingFloorBean;
import com.self.framework.building.vo.FloorComboxVo;

import java.util.List;

public interface BuildingConfigService extends BaseService<BuildingConfigBean> {

    String BUILDINGCONFIG_WATER_RENT_CODE = "WATER_RENT_CONFIG";

    String BUILDINGCONFIG_ELECTRIC_RENT_CODE = "ELECTRIC_RENT_CONFIG";
}
