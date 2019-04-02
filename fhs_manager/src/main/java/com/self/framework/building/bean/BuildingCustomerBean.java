package com.self.framework.building.bean;

import com.self.framework.annotation.Trans;
import com.self.framework.building.service.BuildingCustomerService;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "building_customer")
@Trans
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class BuildingCustomerBean extends BaseBean{

	@Id
	@GenericGenerator(name = "user-uuid", strategy = "uuid")
	@GeneratedValue(generator = "user-uuid")
	// 房客主键
	@Column(name = "customer_id")
	private String customerId;

	// 房客姓名
	@Column(name = "name")
	private String name;

	// 微信号
	@Column(name = "wx_number")
	private String wxNumber;

	// 身份证号
	@Column(name = "id_card")
	private String idCard;

	// 手机号
	@Column(name = "mobile_phone")
	private Long mobilePhone;

	// 出生日期
	@Column(name = "birthday")
	private String birthday;

	// 年龄
	@Column(name = "age")
	private Integer age;

	// 性别
	@Column(name = "sex")
	private Integer sex;

	// 职业信息
	@Column(name = "occupation_info")
	private String occupationInfo;

	// 所属房间
	@Column(name = "ref_room")
	@Trans(transKey = BuildingCustomerService.REF_ROOMID_TO_ROOMCODE_CACHE_KEY)
	private String refRoom;

	// 信用积分(3以下差 6以上良好 8以上优秀)
	@Column(name = "credit_integral")
	private Integer creditIntegral;

	// 居住时常(年 默认创建时间到当前)
	@Column(name = "live_time")
	private Integer liveTime;

	// 户籍地址
	@Column(name = "permanent_addr")
	private String permanentAddr;

	// 紧急联系人
	@Column(name = "urgent_contacts")
	private String urgentContacts;

	// 紧急联系人电话
	@Column(name = "urgent_contacts_phone")
	private Long urgentContactsPhone;

}