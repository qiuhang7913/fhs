package com.self.framework.bill.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.bill.bean.IncomeBillBean;
import com.self.framework.bill.dao.IncomeBillDao;
import com.self.framework.bill.service.IncomeBillService;
import com.self.framework.building.bean.BuildingConfigBean;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.service.BuildingConfigService;
import com.self.framework.building.service.BuildingRoomService;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomeBillServiceImpl extends BaseServiceImpl<IncomeBillBean> implements IncomeBillService {

    @Autowired
    private IncomeBillDao billDao;

    @Autowired
    private BuildingConfigService buildingConfigService;

    @Autowired
    private BuildingRoomService buildingRoomService;

    @Override
    public Integer addOrUpdata(IncomeBillBean v) {
        //水费总计 = (当前水立方数 - 之前水立方数) * 立方收费数
        //电费总计 = (当前点度数 - 之前电度数) * 电度收费数
        //应缴费总计 = 房租 + 水费总计 + 电费总计
        Optional<IncomeBillBean> one = billDao.findById(v.getId());
        if (!one.isPresent()){
            return BusinessCommonConstamt.ONE_CODE;
        }
        IncomeBillBean incomeBillBean = one.get();
        BuildingConfigBean waterConfig = buildingConfigService.findOne(BuildingConfigBean.builder().configCode(BuildingConfigService.BUILDINGCONFIG_WATER_RENT_CODE).build());
        BuildingConfigBean electricEnergyConfig = buildingConfigService.findOne(BuildingConfigBean.builder().configCode(BuildingConfigService.BUILDINGCONFIG_ELECTRIC_RENT_CODE).build());
        BuildingRoomBean roomBean = buildingRoomService.findOneById(incomeBillBean.getRefRoom());
        BigDecimal waterRentTotal = new BigDecimal((v.getCurrWaterCube() - v.getBeforeWaterCube()) * ConvertDataUtil.convertDouble(waterConfig.getConfigValue())).setScale(2, RoundingMode.UP);
        BigDecimal electricRentTotal = new BigDecimal((v.getCurrElectricEnergy() - v.getBeforeElectricEnergy()) * ConvertDataUtil.convertDouble(electricEnergyConfig.getConfigValue())).setScale(2, RoundingMode.UP);
        BigDecimal roomRentTotal = new BigDecimal(roomBean.getRoomToll());

        incomeBillBean.setWaterRentTotal(waterRentTotal);
        incomeBillBean.setElectricRentTotal(electricRentTotal);
        incomeBillBean.setRentTotal(roomRentTotal.add(electricRentTotal).add(waterRentTotal));
        incomeBillBean.setCurrWaterCube(v.getCurrWaterCube());
        incomeBillBean.setCurrElectricEnergy(v.getCurrElectricEnergy());
        incomeBillBean.setStatus(BusinessCommonConstamt.TOW_CODE);
        return super.addOrUpdata(incomeBillBean);
    }

    @Override
    public Integer updateBillAtConfirm(IncomeBillBean v) {
        Optional<IncomeBillBean> one = billDao.findById(v.getId());
        if (!one.isPresent()){
            return BusinessCommonConstamt.ONE_CODE;
        }
        IncomeBillBean incomeBillBean = one.get();
        incomeBillBean.setActualRentTotal(v.getActualRentTotal());
        incomeBillBean.setStatus(BusinessCommonConstamt.THREE_CODE);
        return super.addOrUpdata(incomeBillBean);
    }

    @Override
    public Page<IncomeBillBean> queryListHasPagingAndSort(IncomeBillBean v, Integer pageStart, Integer pageEnd, Integer sortType, String... sortFiled) {
        Page<IncomeBillBean> incomeBillBeans = super.queryListHasPagingAndSort(v, pageStart, pageEnd, sortType, sortFiled);
        List<IncomeBillBean> content = incomeBillBeans.getContent();
        content.forEach(incomeBillBean -> {
            String querySqlRoom = "SELECT room_code AS roomCode FROM building_room WHERE id=:roomId";
            String querySqlCustomer = "SELECT name AS name FROM building_customer WHERE customer_id=:customerId";
            List<Map<String, Object>> roomList = billDao.findOther(querySqlRoom, new HashMap<String, Object>() {
                {
                    put("roomId", incomeBillBean.getRefRoom());
                }
            });
            List<Map<String, Object>> customerList = billDao.findOther(querySqlCustomer, new HashMap<String, Object>() {
                {
                    put("customerId", incomeBillBean.getRefCustomer());
                }
            });
            Map<String,Object> transMap = new HashMap<>();
            if (!ObjectCheckUtil.checkIsNullOrEmpty(roomList)){
                transMap.put("refRoomToTransValue", roomList.get(0).get("roomCode"));
            }
            if (!ObjectCheckUtil.checkIsNullOrEmpty(customerList)){
                transMap.put("refCustomerToTransValue", customerList.get(0).get("name"));
            }
            incomeBillBean.setTransMap(transMap);
        });
        return incomeBillBeans;
    }

    @Override
    public IncomeBillBean findOneById(String id) {
        IncomeBillBean oneById = super.findOneById(id);
        String querySqlRoom = "SELECT room_code AS roomCode FROM building_room WHERE id=:roomId";
        String querySqlCustomer = "SELECT name AS name FROM building_customer WHERE customer_id=:customerId";
        List<Map<String, Object>> roomList = billDao.findOther(querySqlRoom, new HashMap<String, Object>() {
            {
                put("roomId", oneById.getRefRoom());
            }
        });
        List<Map<String, Object>> customerList = billDao.findOther(querySqlCustomer, new HashMap<String, Object>() {
            {
                put("customerId", oneById.getRefCustomer());
            }
        });
        Map<String,Object> transMap = new HashMap<>();
        if (!ObjectCheckUtil.checkIsNullOrEmpty(roomList)){
            transMap.put("refRoomToTransValue", roomList.get(0).get("roomCode"));
        }
        if (!ObjectCheckUtil.checkIsNullOrEmpty(customerList)){
            transMap.put("refCustomerToTransValue", customerList.get(0).get("name"));
        }
        oneById.setTransMap(transMap);
        return oneById;
    }

}
