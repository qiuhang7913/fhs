package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.building.bean.BuildingFloorBean;
import com.self.framework.building.dao.BuildingFloorDao;
import com.self.framework.building.service.BuildingFloorService;
import com.self.framework.building.vo.FloorComboxVo;
import com.self.framework.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingFloorServiceImpl extends BaseServiceImpl<BuildingFloorBean> implements BuildingFloorService {

    @Autowired
    private BuildingFloorDao buildingFloorDao;

    @Override
    public List<FloorComboxVo> findFloorIdAndCode() {
        List<FloorComboxVo> dataList;
        try {
            String querySql = "SELECT bf.floor_name AS text, bf.floor_code AS value, bf.id AS floorId FROM building_floor bf";
            dataList = buildingFloorDao.findOther(querySql, new HashMap<>(), FloorComboxVo.class);
        }catch (Exception e){
            throw new BusinessException("查询异常;" + e.getMessage());
        }
        return dataList;
    }
}
