package com.self.framework.building.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 房间bean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "building_room")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class    BuildingRoomBean extends BaseBean {

    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    /**
     * 房间名
     */
    @Column(name = "room_name", nullable = false, length = 20)
    private String roomName;

    /**
     * 房间码
     */
    @Column(name = "room_code", nullable = false, length = 20)
    private String roomCode;

    /**
     * 状态(0:禁用 1:空置 2:被预定 3:启用 4:维修)
     */
    @Column(name = "status", nullable = false, length = 11)
    private Integer status;

    /**
     * 房间当前拥有者
     * 用户外键
     */
    @Column(name = "owner", nullable = false, length = 64)
    private String owner;

    /**
     * 所在楼层id
     * 楼层外键
     */
    @Column(name = "floor_id", nullable = false, length = 64)
    private String floorId;

    /**
     * 租金(裸房)
     */
    @Column(name = "room_toll", nullable = false, length = 11)
    private Integer roomToll;

    /**
     * 上调水费(若负为下降)
     */
    @Column(name = "water_up_toll", nullable = false)
    private BigDecimal waterUpToll;

    /**
     * 上调电费(若负为下降)
     */
    @Column(name = "electric_up_toll", nullable = false)
    private BigDecimal electricUpToll;

    /**
     * 居住面积
     */
    @Column(name = "area_size", nullable = false)
    private Double areaSize;

    /**
     * 总电度计数
     */
    @Column(name = "total_electric_energy", nullable = false)
    private Double totalElectricEnergy;

    /**
     * 总水立方计数
     */
    @Column(name = "total_water_cube", nullable = false)
    private Double totalWaterCube;

    /**
     * 采光情况(1:阴面 2:阳面)
     */
    @Column(name = "lighting", nullable = false, length = 11)
    private Integer lighting;

    /**
     * 最大居住数
     */
    @Column(name = "max_live", nullable = false, length = 11)
    private Integer maxLive;
}
