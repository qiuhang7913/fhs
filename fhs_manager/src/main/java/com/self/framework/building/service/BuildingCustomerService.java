package com.self.framework.building.service;

import com.self.framework.base.BaseService;
import com.self.framework.building.bean.BuildingCustomerBean;

public interface BuildingCustomerService extends BaseService<BuildingCustomerBean> {
    Integer DEFAULT_CREDIT_INTEGRAL = 6; //默认信用积分
}
