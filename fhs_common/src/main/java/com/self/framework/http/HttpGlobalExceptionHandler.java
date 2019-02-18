package com.self.framework.http;

import com.self.framework.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

/**
 * @des 全局错误封装
 * @author qiuhang
 * @version v1.0
 */
@ControllerAdvice
@Component
public class HttpGlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(BusinessException exception) {
        logger.error("错误信息!",exception);
        return "服务器异常,请联系管理员!\n可能存在一下错误:" + exception.getMessage();
    }
}
