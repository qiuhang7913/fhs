package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.http.PageResult;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
    public PageResult<T> list(@RequestBody String obj, HttpServletRequest request){
        PageResult<T> pageResult = new PageResult<>();
        try {
            ParameterizedType ptClass = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type actualTypeArgument = ptClass.getActualTypeArguments()[0];//获取当前泛型类型
            T t = JSON.parseObject(obj, actualTypeArgument);//手动序列化当前类
            Page<T> pageData = baseService.queryListHasPagingAndSort(t, (t.getPage()-1) * t.getRows(),t.getRows(), BusinessCommonConstamt.ZERO_CODE, "sort");

            if (!ObjectCheckUtil.checkIsNullOrEmpty(pageData)){
                pageResult.setCode(HttpCodeConstant.HTTP_OK_CODE);
                pageResult.setDescribe(HttpCodeConstant.HTTP_OK_CODE_DESCRIBE);
                pageResult.setTotal(pageData.getNumber());
                pageResult.setRows(pageData.getContent());
            }
        }catch (Exception e){
            e.printStackTrace();
            pageResult.setCode(HttpCodeConstant.HTTP_ERROR_CODE);
            pageResult.setDescribe(HttpCodeConstant.HTTP_OK_ERROR_DESCRIBE);
        }
        return pageResult;
    }
}
