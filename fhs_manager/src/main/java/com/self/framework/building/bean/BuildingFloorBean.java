package com.self.framework.building.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * 楼层bean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "building_floor")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class BuildingFloorBean extends BaseBean {

    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    /**
     * 楼层名
     */
    @Column(name = "floor_name", nullable = false, length = 20)
    private String floorName;

    /**
     * 楼层码
     */
    @Column(name = "floor_code", nullable = false, length = 20)
    private String floorCode;

    /**
     * 最大居住数
     */
    @Column(name = "floor_max_live", nullable = false, length = 11)
    private Integer floorMaxLive;

    /**
     * 网络通信状态(1:电信,2:联通,3:主家)
     */
    @Column(name = "floor_network_type", nullable = false, length = 11)
    private Integer floorNetworkType;

    /**
     * 楼层类型(1:门面 2:整租 3:单间)
     */
    @Column(name = "floor_type", nullable = false, length = 11)
    private Integer floorType;
}
