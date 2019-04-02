package com.self.framework.scheduled.dynamicScheduled;

import com.self.framework.utils.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public abstract class MyRunnable implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        logger.info("starting task:****************" + DateTool.getDataStrByLocalDateTime(LocalDateTime.now(),DateTool.FORMAT_L6));
        excute();
        logger.info("ending task:****************" + DateTool.getDataStrByLocalDateTime(LocalDateTime.now(),DateTool.FORMAT_L6));
    }

    protected abstract void excute();
}
