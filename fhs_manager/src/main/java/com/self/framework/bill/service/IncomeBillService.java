package com.self.framework.bill.service;

import com.self.framework.base.BaseService;
import com.self.framework.bill.bean.IncomeBillBean;

public interface IncomeBillService extends BaseService<IncomeBillBean> {
   Integer DEFAULT_STAUTS_STARTUP = 1;

    Integer updateBillAtConfirm(IncomeBillBean incomeBillBean);
}
