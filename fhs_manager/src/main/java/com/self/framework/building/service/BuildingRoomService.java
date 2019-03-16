package com.self.framework.building.service;

import com.self.framework.base.BaseService;
import com.self.framework.building.bean.BuildingRoomBean;

public interface BuildingRoomService extends BaseService<BuildingRoomBean> {
    String FLOOR_ID_TO_NAME_CACHE = "building:room:floorId_to_name:";
}
