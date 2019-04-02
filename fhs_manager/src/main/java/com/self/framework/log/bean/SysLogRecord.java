package com.self.framework.log.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.annotation.Between;
import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @des 用户实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_log_record")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysLogRecord extends BaseBean {
    /**
     *  菜单id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "log_id", nullable = false, length = 64)
    private String logId;

    @Column(name = "record_req_url")
    private String recordReqUrl;//记录请求的连接

    @Column(name = "record_opt_user")
    @NotNull
    private String recordOptUser;//记录操作用户(登录名)

    @Column(name = "record_opt_time")
    @NotNull
    @Between
    private String recordOptTime;//记录操作时间

    @Column(name = "record_opt_remote_ip")
    @NotNull
    private String recordOptRemoteIp;//记录操作远程ip

    @Column(name = "record_opt_des")
    @NotNull
    private String recordOptDes;//记录操作日志备注

    @Column(name = "record_opt_type")
    @NotNull(message = "类型不能为空")
    @Min(value = 0, message = "记录操作类型最小只能0")
    private Integer recordOptType;//记录操作类型(1:修改 2:查询 3:删除)

    @Column(name = "record_opt_result")
    @NotNull(message = "记录操作结果不能为空")
    @Min(value = 0, message = "记录操作结果类型最小只能0")
    private Integer recordOptResult;//记录操作结果(0:成功 1:失败)
}
