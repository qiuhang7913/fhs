package com.self.framework.building.from;

import com.alibaba.fastjson.annotation.JSONField;
import com.self.framework.base.BaseFrom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingCustomerFrom extends BaseFrom {

    @JSONField(ordinal = 1)
    private String id;
}
