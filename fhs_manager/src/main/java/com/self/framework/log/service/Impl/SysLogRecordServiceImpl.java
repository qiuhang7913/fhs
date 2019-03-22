package com.self.framework.log.service.Impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.log.bean.SysLogRecord;
import com.self.framework.log.dao.SysLogRecordDao;
import com.self.framework.log.service.SysLogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogRecordServiceImpl extends BaseServiceImpl<SysLogRecord> implements SysLogRecordService {

    @Autowired
    private SysLogRecordDao sysLogRecordDao;
}
