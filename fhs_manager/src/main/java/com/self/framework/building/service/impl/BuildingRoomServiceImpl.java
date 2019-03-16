package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.building.bean.BuildingFloorBean;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.dao.BuildingFloorDao;
import com.self.framework.building.dao.BuildingRoomDao;
import com.self.framework.building.service.BuildingFloorService;
import com.self.framework.building.service.BuildingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingRoomServiceImpl extends BaseServiceImpl<BuildingRoomBean> implements BuildingRoomService {

    @Autowired
    private BuildingRoomDao buildingRoomDao;
}
