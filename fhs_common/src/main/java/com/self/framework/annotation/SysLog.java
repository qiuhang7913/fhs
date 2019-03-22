package com.self.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, TYPE})
@Retention(RUNTIME)
public @interface SysLog {
    /**
     * 记录操作类型(1:修改 2:查询 3:删除)
     * @return
     */
    String logOptType() default "1";

    /**
     * 描述
     * @return
     */
    String logOptDes () default "";
}
