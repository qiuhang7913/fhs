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
public class BaseAction<T extends BaseBean> extends SuperAction<T> {

    /** service 操作 */
    @Autowired
    private BaseService<T> baseService;

    @RequestMapping(value = "addOrUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> addOrUpdate(@RequestBody String obj){
        Integer addCode = baseService.addOrUpdata(this.transformationRequestParam(obj,true));
        if (addCode == BusinessCommonConstamt.ZERO_CODE){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    /**
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "del", method = {RequestMethod.DELETE})
    @ResponseBody
    public HttpResult<Map> delete(List<String> ids){
        try {
            baseService.delete(ids);
        }catch (Exception e){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public PageResult<T> list(@RequestBody String obj, HttpServletRequest request){
        PageResult<T> pageResult = new PageResult<>();
        try {
            T t = this.transformationRequestParam(obj, false);//手动序列化当前类
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
