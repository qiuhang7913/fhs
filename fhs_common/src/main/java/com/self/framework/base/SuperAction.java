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



    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    protected String goPage(HttpServletRequest request){
        String reqPath = request.getRequestURI();
        String goWhere = ConvertDataUtil.convertStr(request.getParameter("go"));
        if (goWhere.length() != BusinessCommonConstamt.ZERO_CODE){//未获取到所要前往的页面,则默认会跳地址
            goWhere = reqPath + "/" + goWhere;
        }else{
            goWhere = reqPath;
        }
        return goWhere;
    }


}
