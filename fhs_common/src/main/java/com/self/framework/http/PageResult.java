package com.self.framework.http;

import com.self.framework.base.BaseReult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @des 后台列表分页返回结果
 * @author qiuhang
 * @version v.1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> extends BaseReult {
    private Long total;

    private List<T> rows;
}
