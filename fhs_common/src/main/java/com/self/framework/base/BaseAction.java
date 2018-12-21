package com.self.framework.base;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @des 公共 crud action
 * @author qiuhang
 * @version v1.0
 */
public class BaseAction<T extends BaseBean> extends SuperAction {

    /** service 操作 */
    @Autowired
    private BaseService<T> baseService;

    @RequestMapping(value = "add", consumes="application/json", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> add(@Valid @RequestBody T obj){
        Integer addCode = baseService.add(obj);
        if (addCode == BusinessCommonConstamt.ZERO_CODE){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "update", consumes="application/json", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> update(@Valid @RequestBody T obj){
        Integer addCode = baseService.update(obj);
        if (addCode == BusinessCommonConstamt.ZERO_CODE){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<List> add(@Valid @RequestBody T obj, HttpServletRequest request){
        List queryDataList = baseService.queryList(obj);
        return ObjectCheckUtil.checkIsNullOrEmpty(queryDataList) ? HttpResult.aOtherResult(HttpCodeConstant.HTTP_ERROR_CODE, HttpCodeConstant.HTTP_OK_ERROR_DESCRIBE, new ArrayList<>())
                                                                    :
                                                                    HttpResult.aOtherResult(HttpCodeConstant.HTTP_OK_CODE, HttpCodeConstant.HTTP_OK_CODE_DESCRIBE, queryDataList);
    }
}
