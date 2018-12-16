package com.self.framework.ucenter.service;

import com.self.framework.ucenter.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @des: spring security 核心认证授权
 * @author qiuhang
 * @version v1.0
 */
public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserSevice userSevice;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        //--------------------认证账号
        SysUser user = userSevice.loadUserByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }


        //-------------------开始授权
//        List<Menu> menus = menuService.getMenusByUserId(user.getId());
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Menu menu : menus) {
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(menu.getUrl());
//            //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
//            grantedAuthorities.add(grantedAuthority);
//        }
//        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
