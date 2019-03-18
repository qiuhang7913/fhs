package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.building.bean.BuildingConfigBean;
import com.self.framework.building.dao.BuildingConfigDao;
import com.self.framework.building.service.BuildingConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingConfigServiceImpl extends BaseServiceImpl<BuildingConfigBean> implements BuildingConfigService {

    @Autowired
    private BuildingConfigDao buildingConfigDao;

}
