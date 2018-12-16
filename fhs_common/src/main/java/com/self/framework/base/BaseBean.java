package com.self.framework.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Map;

/**
 * @des 公共bean
 * @author qiuhang
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseBean implements Serializable {

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "update_user")
    private String updateUser;

    @Transient
    private Map<String,String> between;

}
