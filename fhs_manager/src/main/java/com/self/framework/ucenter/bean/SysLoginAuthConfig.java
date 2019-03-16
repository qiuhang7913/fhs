package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户登陆认证方式配置bean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "login_auth_config")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysLoginAuthConfig extends BaseBean {
    /**
     *  配置id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "config_id", nullable = false, length = 64)
    private String configId;

    /**
     * 登陆账号
     */
    @Column(name = "login_name", nullable = false, length = 24)
    private String loginName;

    /**
     * 是否禁止登陆
     */
    @Column(name = "is_ban", nullable = false, length = 24)
    private Integer isBan;

    /**
     * 登陆认证类型
     * 1:静态密码登陆，2：手机短信验证登陆(目前支持这两种)
     */
    @Column(name = "login_auth_type", nullable = false, length = 64)
    private String loginAuthType;
}
