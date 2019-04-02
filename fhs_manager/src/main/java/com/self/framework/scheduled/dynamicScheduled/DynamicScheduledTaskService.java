package com.self.framework.scheduled.dynamicScheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

/**
 * @des 动态定时任务service
 * @author qiuhang
 * @version v1.0
 */
@Service
public class DynamicScheduledTaskService {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    /**
     * 开启定时任务
     * @param myRunnable
     * @param cron
     * @return
     */
    public String startCron(MyRunnable myRunnable, String cron){
        future = threadPoolTaskScheduler.schedule(myRunnable, new CronTrigger(cron));
        return "ok!";
    }

    /**
     * 停止定时任务
     * @return
     */
    public String stopCron() {
        if (future != null) {
            future.cancel(true);

        }
        return "stop";
    }
}
