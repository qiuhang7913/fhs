package com.self.framework.spring.security.extend;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @des: 自定义密码比较器,用于密码加密,暂不加密
 * @author qiuhang
 * @version v1.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }


    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence);
    }

}
