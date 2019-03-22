package com.self.framework.log.aop;

import com.alibaba.fastjson.JSON;
import com.self.framework.annotation.SysLog;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.http.PageResult;
import com.self.framework.log.bean.SysLogRecord;
import com.self.framework.log.service.SysLogKafkaService;
import com.self.framework.message.kafka.KafkaService;
import com.self.framework.spring.extend.ObtainSpringBean;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.DateTool;
import com.self.framework.utils.ObjectCheckUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @des: 系统操作日志记录aop
 * @author qiuhang
 * @version v1.0
 */
@Aspect
@Component
public class SysLogAcpect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private SysLogKafkaService kafkaService;

    /**
     * @des: 切入点
     */
    @Pointcut("execution(public * com.self.framework.base.BaseAction.*(..)) || " +
            "execution(public * com.self.framework.*.action.*.*(..))")
    public void sysLog(){}

    /**
     * @des: 执行之前
     * @param joinPoint
     * @throws Throwable
     */
    @Before(value = "sysLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
    }

    /**
     * 执行之后
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(value = "sysLog()", returning = "returnVal")
    public void doAfterAdvice(JoinPoint joinPoint, Object returnVal){
        SysLog sysLogAnnotation = joinPoint.getTarget().getClass().getAnnotation(SysLog.class);
        //当前类不做日志
        if (ObjectCheckUtil.checkIsNullOrEmpty(sysLogAnnotation)){
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectCheckUtil.checkIsNullOrEmpty(attributes)){
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SysLog sysLogAnnotationMethod = signature.getMethod().getAnnotation(SysLog.class);
        HttpServletRequest request = attributes.getRequest();
        HttpResult httpResult = null;
        PageResult pageResult = null;
        String logDes = "";
        if (returnVal instanceof HttpResult){
            httpResult = (HttpResult)returnVal;
        }else if (returnVal instanceof PageResult){
            pageResult = (PageResult)returnVal;
        }
        if (!ObjectCheckUtil.checkIsNullOrEmpty(sysLogAnnotationMethod.logOptDes())){
            logDes = sysLogAnnotationMethod.logOptDes();
        }else{
            if (BusinessCommonConstamt.ONE_STRING_CODE.equals(sysLogAnnotationMethod.logOptType())){//说明是修改
                logDes = "修改数据";
            }else if (BusinessCommonConstamt.TOW_STRING_CODE.equals(sysLogAnnotationMethod.logOptType())){
                String listData = "";
                if (!ObjectCheckUtil.checkIsNullOrEmpty(httpResult)){
                    listData =  ",本次查询成功相应数据个数：" + JSON.parseArray(JSON.toJSONString(httpResult.getResult())).size();
                }
                if (!ObjectCheckUtil.checkIsNullOrEmpty(pageResult)){
                    listData =  ",本次查询成功相应数据个数：" + pageResult.getTotal();
                }
                logDes = "查询数据" + listData;
            }else{
                logDes = "删除数据";
            }
        }
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysLogRecord build = SysLogRecord.builder().recordOptRemoteIp(request.getRemoteAddr().toString())
                .recordOptType(ConvertDataUtil.convertInt(sysLogAnnotationMethod.logOptType()))
                .recordOptUser(sysUser.getLoginName())
                .recordReqUrl(request.getRequestURL().toString())
                .recordOptResult(httpResult == null ? BusinessCommonConstamt.ZERO_CODE : (HttpCodeConstant.HTTP_OK_CODE.equals(httpResult.getCode()) ? BusinessCommonConstamt.ZERO_CODE : BusinessCommonConstamt.ONE_CODE))
                .recordOptDes(logDes)
                .recordOptTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L6))
                .build();
        // 记录下请求内容

        if (kafkaService == null){
            kafkaService = ObtainSpringBean.getBean(SysLogKafkaService.class);
        }
        kafkaService.sendMsg(build);
    }


    @AfterThrowing(pointcut = "sysLog()", throwing = "e")//切点在webpointCut()
    public void handleThrowing(JoinPoint joinPoint, Exception e) {//controller类抛出的异常在这边捕获
        SysLog sysLogAnnotation = joinPoint.getTarget().getClass().getAnnotation(SysLog.class);
        //当前类不做日志
        if (ObjectCheckUtil.checkIsNullOrEmpty(sysLogAnnotation)){
            return;
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectCheckUtil.checkIsNullOrEmpty(attributes)){
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SysLog sysLogAnnotationMethod = signature.getMethod().getAnnotation(SysLog.class);
        HttpServletRequest request = attributes.getRequest();
        String logDes = "操作异常,异常可能为:" + e.getMessage() + "所在类:" + className + "异常所在方法:" + methodName;

        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysLogRecord build = SysLogRecord.builder().recordOptRemoteIp(request.getRemoteAddr().toString())
                .recordOptType(ConvertDataUtil.convertInt(sysLogAnnotationMethod.logOptType()))
                .recordOptUser(sysUser.getLoginName())
                .recordReqUrl(request.getRequestURL().toString())
                .recordOptResult(BusinessCommonConstamt.ONE_CODE)
                .recordOptDes(logDes)
                .recordOptTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L6))
                .build();

        System.out.println(JSON.toJSONString(build));
    }
}
