package com.self.framework.log.action;

import com.self.framework.base.BaseAction;
import com.self.framework.log.bean.SysLogRecord;
import com.self.framework.log.service.SysLogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/log/sys_log")
public class SysLogAction extends BaseAction<SysLogRecord> {

    @Autowired
    private SysLogRecordService sysLogRecordService;
}
