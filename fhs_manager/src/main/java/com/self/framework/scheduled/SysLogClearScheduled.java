package com.self.framework.scheduled;

import com.self.framework.log.bean.SysLogRecord;
import com.self.framework.log.service.SysLogRecordService;
import com.self.framework.utils.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @des 定时清理后管日志
 * @author qiuhang
 * @version v1.0
 */
@Component
@EnableScheduling
public class SysLogClearScheduled {

    @Autowired
    SysLogRecordService sysLogRecordService;

    /**
     * 定时任务：清理上月日志
     */
    @Scheduled(cron = "0 0 0 1 * ? ")
//    @Scheduled(cron = "0 13 11 * * ? ")
    public void scheduled(){
        String now = DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3);
        String lastManth = DateTool.getDataStrByLocalDateTime(LocalDateTime.now().plusMonths(-1), DateTool.FORMAT_L3);
        SysLogRecord build = SysLogRecord.builder().build();
        build.setBetween(new HashMap<String, String>(){{
            put("start_recordOptTime", now);
            put("end_recordOptTime", lastManth);
        }});
        List<String> sysLogRecords = sysLogRecordService.queryList(build).stream().map(SysLogRecord :: getLogId).collect(Collectors.toList());
        sysLogRecordService.delete(sysLogRecords);
    }

    public static void main(String[] args) {
        LocalDate localDateByDateStr = DateTool.getLocalDateByDateStr("2019-04-01", DateTool.FORMAT_L3);
        System.out.println(localDateByDateStr.getDayOfMonth());
    }
}
