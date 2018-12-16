package com.self.framework.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @des 基础result
 * @author qiuhang
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
