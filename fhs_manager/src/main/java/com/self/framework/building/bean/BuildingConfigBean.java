package com.self.framework.building.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 房屋配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "building_config")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class BuildingConfigBean extends BaseBean {

    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "config_id", nullable = false, length = 64)
    private String configId;

    @Column(name = "config_code", nullable = false, length = 20)
    private String configCode;

    @Column(name = "config_name", nullable = false, length = 20)
    private String configName;

    @Column(name = "config_value", nullable = false, length = 20)
    private String configValue;
}
