package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.exception.BusinessException;
import com.self.framework.http.HttpResult;
import com.self.framework.http.PageResult;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @des 公共 crud action
 * @author qiuhang
 * @version v1.0
 */
public class BaseAction<T extends BaseBean> extends SuperAction {

    /** service 操作 */
    @Autowired
    private BaseService<T> baseService;

    /** hibernate校验器 */
    @Autowired
    private Validator validator;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @RequestMapping(value = "addOrUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public HttpResult<Map> addOrUpdate(@RequestBody String obj){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG)){
            throw new BusinessException("当前用户没有添加获取修改权限!");
        }
        Integer addCode = baseService.addOrUpdata(this.transformationRequestParam(obj,true));
        if (addCode == BusinessCommonConstamt.ZERO_CODE){
            return HttpResult.errorResult();
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "obtainOne/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public HttpResult<T> obtainOne(@PathVariable(value = "id" ) String id){
        if (!isPermission(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG)){
            throw new BusinessException("当前用户没有查看权限!");
        }
        T one = baseService.findOneById(id);
        if (!ObjectCheckUtil.checkIsNullOrEmpty(one)){
            return HttpResult.okOtherDataResult(one);
        }
        return HttpResult.errorOtherDataResult(one);
    }

    /**
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "del", method = {RequestMethod.DELETE})
    @ResponseBody
    public HttpResult<Map> delete(@RequestBody List<String> ids){
        try {
            baseService.delete(ids);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        return HttpResult.okResult();
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public PageResult<T> list(@RequestBody String obj , HttpServletRequest request){
        PageResult<T> pageResult = new PageResult<>();
        try {
            T t = this.transformationRequestParam(obj, false);//手动序列化当前类
            Page<T> pageData = baseService.queryListHasPagingAndSort((T)t, (t.getPage()-1) * t.getRows(),t.getRows(), t.getSortOrder(), t.getSortFiled());

            if (!ObjectCheckUtil.checkIsNullOrEmpty(pageData)){
                pageResult.setCode(HttpCodeConstant.HTTP_OK_CODE);
                pageResult.setDescribe(HttpCodeConstant.HTTP_OK_CODE_DESCRIBE);
                pageResult.setTotal(pageData.getTotalElements());
                pageResult.setRows(pageData.getContent());
            }
        }catch (Exception e){
            e.printStackTrace();
            pageResult.setCode(HttpCodeConstant.HTTP_ERROR_CODE);
            pageResult.setDescribe(HttpCodeConstant.HTTP_OK_ERROR_DESCRIBE);
        }
        return pageResult;
    }
    /**
     * bean手动转化于校验
     * @param requestParam
     * @param needViolation
     * @return
     */
    private T transformationRequestParam(String requestParam, boolean needViolation){
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

    private boolean isPermission(String action){
        RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
        String nameSpace = requestMapping.value()[0];
        UserDetails sessionUserInfo = this.getSessionUserInfo();
        return sessionUserInfo.getAuthorities().contains(new SimpleGrantedAuthority(nameSpace + ":" + action));
    }
    /**
     *
     * @return
     */
    protected UserDetails getSessionUserInfo(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static void main(String[] args) {
        String aa = "/aa/bb";
        System.out.println(aa.substring(1).replace("/","."));
    }
}
