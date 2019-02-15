package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @des: 用户与角色多对多关系bean
 * @author qiuhang
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_role")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUserWithRole extends BaseBean {
    /**
     *  主键id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;

    @Column(name = "role_id", nullable = false, length = 64)
    private String roleId;
}
