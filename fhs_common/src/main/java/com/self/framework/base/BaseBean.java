package com.self.framework.base;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Map<String, String> getBetween() {
        return between;
    }

    public void setBetween(Map<String, String> between) {
        this.between = between;
    }
}
