package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.utils.ConvertDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @des 基础action
 * @author qiuhang
 * @version v1.0
 */
public class SuperAction<T> {

    @Autowired
    private Validator validator;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String goPage(HttpServletRequest request){
        String reqPath = request.getRequestURI();
        String goWhere = ConvertDataUtil.convertStr(request.getParameter("go"));
        if (goWhere.length() != BusinessCommonConstamt.ZERO_CODE){//未获取到所要前往的页面,则默认会跳地址
            goWhere = reqPath + "/" + goWhere;
        }else{
            goWhere = reqPath;
        }
        return goWhere;
    }

    /**
     * bean手动转化于校验
     * @param requestParam
     * @param needViolation
     * @return
     */
    protected T transformationRequestParam(String requestParam, boolean needViolation){
        ParameterizedType ptClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type actualTypeArgument = ptClass.getActualTypeArguments()[0];//获取当前泛型类型
        T t = JSON.parseObject(requestParam, actualTypeArgument);//手动序列化当前类

        Set<ConstraintViolation<T>> violationSet = validator.validate(t);
        StringBuilder sb = new StringBuilder();
        if (needViolation){
            if (violationSet.size() > BusinessCommonConstamt.ZERO_CODE){//校验不通过
                for (ConstraintViolation<T> item : violationSet) {
                    sb.append(item.getMessage() + ",");
                }
                throw new BusinessException(sb.toString());
            }
        }

        return t;
    }
}
