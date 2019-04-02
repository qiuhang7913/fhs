package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.bill.bean.IncomeBillBean;
import com.self.framework.bill.service.IncomeBillService;
import com.self.framework.building.bean.BuildingCustomerBean;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.dao.BuildingCustomerDao;
import com.self.framework.building.service.BuildingCustomerService;
import com.self.framework.building.service.BuildingRoomService;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.nosql.redis.StringRedisService;
import com.self.framework.scheduled.dynamicScheduled.DynamicScheduledTaskService;
import com.self.framework.scheduled.dynamicScheduled.MyRunnable;
import com.self.framework.utils.DateTool;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingCustomerServiceImpl extends BaseServiceImpl<BuildingCustomerBean> implements BuildingCustomerService {

    @Autowired
    private BuildingCustomerDao buildingCustomerDao;

    @Autowired
    private BuildingRoomService buildingRoomService;

    @Autowired
    private StringRedisService redisService;

    @Autowired
    private DynamicScheduledTaskService dynamicScheduledTaskService;

    @Autowired
    private IncomeBillService billService;

    @Override
    public Integer addOrUpdata(BuildingCustomerBean buildingCustomerBean) {
        String querySql = "SELECT room_code AS roomCode FROM building_room WHERE id = :roomId";
        List<Map<String, Object>> queryDataList = buildingCustomerDao.findOther(querySql, new HashMap<String, Object>() {{
            put("roomId", buildingCustomerBean.getRefRoom());
        }});
        if (!ObjectCheckUtil.checkIsNullOrEmpty(queryDataList)){
            redisService.insertObj(REF_ROOMID_TO_ROOMCODE_CACHE_KEY + buildingCustomerBean.getRefRoom(), queryDataList.get(0).get("roomCode").toString());
        }
        BuildingCustomerBean newCustomerBean = this.saveCurrData(buildingCustomerBean);
        if (ObjectCheckUtil.checkIsNullOrEmpty(buildingCustomerBean.getCustomerId()) &&
                !ObjectCheckUtil.checkIsNullOrEmpty(newCustomerBean)){//添加房客操作并且添加房客成功
            //新建当前房客房租账单定时任务
            String cron = "0 0 0 " + DateTool.getLocalDateByDateStr(buildingCustomerBean.getCreateTime(), DateTool.FORMAT_L3) + " * ? *";
//            String cron = "0 28 17 * * ? ";
            dynamicScheduledTaskService.startCron(new MyRunnable() {
                @Override
                protected void excute() {
                    //初始化账单
                    BuildingRoomBean roomOne = buildingRoomService.findOne(BuildingRoomBean.builder().id(newCustomerBean.getRefRoom()).build());
                    billService.addOrUpdata(
                            IncomeBillBean.builder()
                                    .recordTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(),DateTool.FORMAT_L6))
                                    .refRoom(newCustomerBean.getRefRoom())
                                    .refCustomer(newCustomerBean.getCustomerId())
                                    .waterRentTotal(BigDecimal.ZERO)
                                    .electricRentTotal(BigDecimal.ZERO)
                                    .rentTotal(new BigDecimal(roomOne.getRoomToll()))
                                    .status(IncomeBillService.DEFAULT_STAUTS_STARTUP)
                                    .beforeElectricEnergy(roomOne.getTotalElectricEnergy())
                                    .currElectricEnergy(0.00)
                                    .beforeWaterCube(roomOne.getTotalWaterCube())
                                    .currWaterCube(0.00)
                                    .actualRentTotal(BigDecimal.ZERO)
                                    .build()
                    );
                }
            }, cron);
        }
        return ObjectCheckUtil.checkIsNullOrEmpty(newCustomerBean)? BusinessCommonConstamt.ZERO_CODE : BusinessCommonConstamt.ONE_CODE;
    }

    /**
     * 根据业务重写保存方法
     * @param v
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BuildingCustomerBean saveCurrData(BuildingCustomerBean v){
        BuildingCustomerBean one = findOne(v);
        String userName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectCheckUtil.checkIsNullOrEmpty(authentication)){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userName = userDetails.getUsername();
        }

        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            String createTime = DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3);
            v.setCreateTime(createTime);
            v.setCreateUser(userName);
            if (ObjectCheckUtil.checkIsNullOrEmpty(v.getAge())){
                v.setAge(
                        DateTool.calculationYearDifference(
                                DateTool.getLocalDateByDateStr(v.getBirthday(),DateTool.FORMAT_L3),
                                LocalDate.now()
                        )
                );
            }
            v.setLiveTime(
                    DateTool.calculationYearDifference(
                            DateTool.getLocalDateByDateStr(createTime,DateTool.FORMAT_L3),
                            LocalDate.now()
                    )
            );
            v.setCreditIntegral(DEFAULT_CREDIT_INTEGRAL);
        }else{
            v.setCreateTime(one.getCreateTime());
            v.setCreateUser(one.getCreateUser());
            v.setUpdateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
            v.setUpdateUser(userName);
        }
        return buildingCustomerDao.saveAndFlush(v);
    }
}
