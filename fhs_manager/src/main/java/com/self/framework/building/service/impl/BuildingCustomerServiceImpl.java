package com.self.framework.building.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.building.bean.BuildingCustomerBean;
import com.self.framework.building.dao.BuildingCustomerDao;
import com.self.framework.building.service.BuildingCustomerService;
import com.self.framework.utils.DateTool;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BuildingCustomerServiceImpl extends BaseServiceImpl<BuildingCustomerBean> implements BuildingCustomerService {

    @Autowired
    private BuildingCustomerDao buildingCustomerDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdata(BuildingCustomerBean v) {
        return ObjectCheckUtil.checkIsNullOrEmpty(this.saveCurrData(v)) ? 0 : 1;
    }

    /**
     *
     * @param v
     * @return
     */
    private BuildingCustomerBean saveCurrData(BuildingCustomerBean v){
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
