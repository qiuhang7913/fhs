package com.self.framework.http;

import com.self.framework.base.BaseReult;
import com.self.framework.constant.HttpCodeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @des http请求返回结果
 * @author qiuhang
 * @version v.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult<T> extends BaseReult {

    private T result;

    public static HttpResult<Map> okResult(){
        HttpResult<Map> httpResult = new HttpResult<>();
        httpResult.setCode(HttpCodeConstant.HTTP_OK_CODE);
        httpResult.setDescribe(HttpCodeConstant.HTTP_OK_CODE_DESCRIBE);
        httpResult.setResult(new HashMap<>());
        return httpResult;
    }

    public static HttpResult<Map> errorResult(){
        return errorOtherCodeResult(HttpCodeConstant.HTTP_ERROR_CODE);
    }

    public static HttpResult<Map> errorOtherCodeResult(Integer code){
        return errorOtherCodeAndDesResult(code, HttpCodeConstant.HTTP_OK_ERROR_DESCRIBE);
    }

    public static HttpResult<Map> errorOtherCodeAndDesResult(Integer code, String des){
        HttpResult<Map> httpResult = new HttpResult<>();
        httpResult.setCode(code);
        httpResult.setDescribe(des);
        httpResult.setResult(new HashMap<>());
        return httpResult;
    }

    public static <V> HttpResult<V> aOtherResult(Integer code, String des, V obj){
        HttpResult<V> httpResult = new HttpResult<>();
        httpResult.setCode(code);
        httpResult.setDescribe(des);
        httpResult.setResult(obj);
        return httpResult;
    }
}
