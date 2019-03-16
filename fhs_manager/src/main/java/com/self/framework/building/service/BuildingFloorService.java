package com.self.framework.building.service;

import com.self.framework.base.BaseService;
import com.self.framework.building.bean.BuildingFloorBean;
import com.self.framework.building.vo.FloorComboxVo;

import java.util.List;

public interface BuildingFloorService extends BaseService<BuildingFloorBean> {

    /**
     * 楼层id和楼层编码查询
     * @return
     */
    List<FloorComboxVo> findFloorIdAndCode();
}
