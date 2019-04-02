package com.self.framework.building.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.self.framework.base.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingCustomerVo extends BaseVo {

    @JSONField(ordinal = 1)
    private String name;

}
