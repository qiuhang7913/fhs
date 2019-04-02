package com.self.framework.bill.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.bill.bean.IncomeBillBean;
import com.self.framework.bill.dao.IncomeBillDao;
import com.self.framework.bill.service.IncomeBillService;
import com.self.framework.building.bean.BuildingConfigBean;
import com.self.framework.building.bean.BuildingCustomerBean;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.service.BuildingConfigService;
import com.self.framework.building.service.BuildingCustomerService;
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

    @Autowired
    private BuildingCustomerService buildingCustomerService;

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
        BigDecimal waterRent = new BigDecimal(ConvertDataUtil.convertDouble(waterConfig.getConfigValue())).setScale(2, RoundingMode.UP)
                                .add(roomBean.getWaterUpToll()) ;//水费立方收费 = 系统立方收费 + 当前房间上调
        BigDecimal waterRentTotal = new BigDecimal((v.getCurrWaterCube() - v.getBeforeWaterCube())).setScale(2, RoundingMode.UP)
                                    .multiply(waterRent);   //水费总计 = (当前水立方数 - 之前水立方数) * 水费立方收费
        BigDecimal electricEnergyRent = new BigDecimal(ConvertDataUtil.convertDouble(electricEnergyConfig.getConfigValue())).setScale(2, RoundingMode.UP)
                .add(roomBean.getElectricUpToll()) ;    //电费电度收费 = 系统电度收费 + 当前房间上调
        BigDecimal electricRentTotal = new BigDecimal((v.getCurrElectricEnergy() - v.getBeforeElectricEnergy())).setScale(2, RoundingMode.UP)
                                        .multiply(electricEnergyRent);    //电费总计 = (当前电度数 - 之前电度数) * 电费电度收费
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

        if (incomeBillBean.getRentTotal().compareTo(v.getActualRentTotal()) < BusinessCommonConstamt.ZERO_CODE){//实收大于应收
            //增加房客可冲减金额
            BuildingCustomerBean customerBean = buildingCustomerService.findOneById(incomeBillBean.getRefCustomer());
            if (!ObjectCheckUtil.checkIsNullOrEmpty(customerBean)){
                customerBean.setBlankingRent(customerBean.getBlankingRent().add(v.getActualRentTotal().subtract(v.getRentTotal())));
                buildingCustomerService.addOrUpdata(customerBean);//更新
            }
        }

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
        String querySqlRoom = "SELECT floor_id AS floorId, room_code AS roomCode, room_name AS roomName, water_up_toll AS waterUpToll, electric_up_toll AS electricUpToll FROM building_room WHERE id=:roomId";//客房查询
        String querySqlCustomer = "SELECT name AS name FROM building_customer WHERE customer_id=:customerId";//客户查询
        String querySqlfloor = "SELECT floor_code AS floorCode, floor_name AS floorName FROM building_floor WHERE id=:floorId";//楼层查询
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
            List<Map<String, Object>> floorList = billDao.findOther(querySqlfloor, new HashMap<String, Object>() {
                {
                    put("floorId", roomList.get(0).get("floorId"));
                }
            });
            BuildingConfigBean waterConfig = buildingConfigService.findOne(BuildingConfigBean.builder().configCode(BuildingConfigService.BUILDINGCONFIG_WATER_RENT_CODE).build());
            BuildingConfigBean electricEnergyConfig = buildingConfigService.findOne(BuildingConfigBean.builder().configCode(BuildingConfigService.BUILDINGCONFIG_ELECTRIC_RENT_CODE).build());
            if (!ObjectCheckUtil.checkIsNullOrEmpty(floorList)){
                transMap.put("refFloorToTransValue", floorList.get(0).get("floorCode"));
                transMap.put("refFloorNameToTransValue", floorList.get(0).get("floorName"));
            }
            if (!ObjectCheckUtil.checkIsNullOrEmpty(waterConfig)){
                Double sysWaterRent = ConvertDataUtil.convertDouble(waterConfig.getConfigValue());//系统水费
                Double currRoomWaterRentUp = ConvertDataUtil.convertDouble(roomList.get(0).get("waterUpToll"));//当前房间上调
                transMap.put("waterRent", new BigDecimal(sysWaterRent).add(new BigDecimal(currRoomWaterRentUp)));//水费 = 系统水费 + 当前房间上调
            }
            if (!ObjectCheckUtil.checkIsNullOrEmpty(electricEnergyConfig)){
                Double sysElectricEnergyRent = ConvertDataUtil.convertDouble(electricEnergyConfig.getConfigValue());//系统电费
                Double currRoomElectricEnergyRentUp = ConvertDataUtil.convertDouble(roomList.get(0).get("electricUpToll"));//当前房间上调
                transMap.put("electricEnergyRent", new BigDecimal(sysElectricEnergyRent).add(new BigDecimal(currRoomElectricEnergyRentUp)));//电费 = 系统电费 + 当前房间上调
            }
            transMap.put("refRoomToTransValue", roomList.get(0).get("roomCode"));
            transMap.put("refRoomNameToTransValue", roomList.get(0).get("roomName"));
        }
        if (!ObjectCheckUtil.checkIsNullOrEmpty(customerList)){
            transMap.put("refCustomerToTransValue", customerList.get(0).get("name"));
        }
        oneById.setTransMap(transMap);
        return oneById;
    }

}
