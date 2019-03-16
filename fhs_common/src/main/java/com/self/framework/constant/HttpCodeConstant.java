package com.self.framework.constant;

/**
 * @des HTTP 相应信息常量
 * @author qiuhang
 * @version v1.0
 */
public class HttpCodeConstant {
    public static final Integer HTTP_OK_CODE = 200;
    public static final String HTTP_OK_CODE_DESCRIBE = "请求成功!";

    public static final Integer HTTP_ERROR_CODE = 500;
    public static final String HTTP_OK_ERROR_DESCRIBE = "系统内部报错,请联系管理员!";

    public static final Integer HTTP_FIND_ERROR_CODE = 201;
    public static final String  HTTP_FIND_ERROR_DESCRIBE = "数据查询错误,可能数据不存在!";
}
