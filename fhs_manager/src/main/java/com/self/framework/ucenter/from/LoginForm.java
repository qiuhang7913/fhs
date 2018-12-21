package com.self.framework.ucenter.from;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseFrom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @des 登录form
 * @author qiuhang
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginForm extends BaseFrom {

    @NotNull
    @NotEmpty
    private String loginName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String verificationCode;

}
