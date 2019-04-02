package com.self.framework.building.service;

import com.self.framework.base.BaseService;
import com.self.framework.building.bean.BuildingCustomerBean;

public interface BuildingCustomerService extends BaseService<BuildingCustomerBean> {
    Integer DEFAULT_CREDIT_INTEGRAL = 6; //默认信用积分
    String REF_ROOMID_TO_ROOMCODE_CACHE_KEY = "building:customer:roomId_to_name:";//客户所属房间的id与房间code的redis缓存key
}
