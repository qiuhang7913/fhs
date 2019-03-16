package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.dao.BuildingFloorDao;
import com.self.framework.building.dao.BuildingRoomDao;
import com.self.framework.building.service.BuildingRoomService;
import com.self.framework.nosql.redis.StringRedisService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingRoomServiceImpl extends BaseServiceImpl<BuildingRoomBean> implements
        BuildingRoomService {

    @Autowired
    private BuildingRoomDao buildingRoomDao;

    @Autowired
    private BuildingFloorDao buildingFloorDao;

    @Autowired
    private StringRedisService redisService;

    @Override
    public Integer addOrUpdata(BuildingRoomBean buildingRoomBean) {
        //缓存更新
        String querySql = "SELECT floor_code AS floorCode FROM building_floor WHERE id = :floorId";
        List<Map<String, Object>> queryDataList = buildingFloorDao.findOther(querySql, new HashMap<String, Object>() {{
            put("floorId", buildingRoomBean.getFloorId());
        }});
        if (!ObjectCheckUtil.checkIsNullOrEmpty(queryDataList)){
            redisService.insertObj(FLOOR_ID_TO_NAME_CACHE + buildingRoomBean.getId(), queryDataList.get(0).get("floorCode").toString());
        }
        //缓存更新
        return super.addOrUpdata(buildingRoomBean);
    }

}
