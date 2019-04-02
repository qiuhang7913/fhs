package com.self.framework.bill.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.annotation.Trans;
import com.self.framework.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Trans
public class IncomeBillBean extends BaseBean{

	@Id
	@GenericGenerator(name = "user-uuid", strategy = "uuid")
	@GeneratedValue(generator = "user-uuid")
	// 账单id
	@Column(name = "id")
	private String id;

	// 账单记账时间
	@Column(name = "record_time")
	private String recordTime;

	// 所属房间
	@Column(name = "ref_room")
	private String refRoom;

	// 所属用户
	@Column(name = "ref_customer")
	private String refCustomer;

	// 当前水费总计
	@Column(name = "water_rent_total")
	private BigDecimal waterRentTotal;

	// 当前电费总计
	@Column(name = "electric_rent_total")
	private BigDecimal electricRentTotal;

	// 当前缴费总计
	@Column(name = "rent_total")
	private BigDecimal rentTotal;

	// 账单状态(1:已启动,2:已生成,3:已缴费)
	@Column(name = "status")
	private Integer status;

	// 之前电度数
	@Column(name = "before_electric_energy")
	private Double beforeElectricEnergy;

	// 当前点度数
	@Column(name = "curr_electric_energy")
	private Double currElectricEnergy;

	// 之前水立方数
	@Column(name = "before_water_cube")
	private Double beforeWaterCube;

	// 当前水立方数
	@Column(name = "curr_water_cube")
	private Double currWaterCube;

	// 当前实收总计
	@Column(name = "actual_rent_total")
	private BigDecimal actualRentTotal;
}