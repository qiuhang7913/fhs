package com.self.framework.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @des 基础result
 * @author qiuhang
 * @version v1.0
 */
@Data
public class BaseReult implements Serializable {

    /**
     * 返回操作code 码
     */
    private Integer code;

    /**
     * 返回操作描述
     */
    private String describe;
}
