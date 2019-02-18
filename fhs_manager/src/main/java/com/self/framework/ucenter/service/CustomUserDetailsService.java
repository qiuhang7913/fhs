package com.self.framework.ucenter.service;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.bean.SysUser;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

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
        user.setUsername(user.getLoginName());//用户账号赋值
        //...
        obtainUserAuths(user);//权限赋值
        return user;
    }

    /**
     * 赋值权限
     * @param sysUser
     */
    private void obtainUserAuths(SysUser sysUser){
        List<GrantedAuthority> auths = new ArrayList<>();
        Set<String> authResources = new HashSet<>();
        List<SysRole> userRoleBeans = sysUser.getUserRoles();//用户权限
        if (sysUser.getType().equals(BusinessCommonConstamt.TOW_CODE)){//超管
            authResources.add("super");
        }
        //权限资源
        userRoleBeans.stream().map(SysRole::getSysMenuResources).forEach(sysMenuResources -> sysMenuResources.forEach(sysMenuResource -> {
            String name = sysMenuResource.getNameSpace();//资源名称
            List<SysMenuResourceFunc> sysMenuResourceFuncs = sysMenuResource.getSysMenuResourceFuncs();
            sysMenuResourceFuncs.forEach(sysMenuResourceFunc -> {
                String authFlag = name + ":";
                Integer funcType = sysMenuResourceFunc.getFuncType();
                if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG;
                }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_FLAG;
                }else if (funcType.intValue() == BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_CODE.intValue()){
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_FLAG;
                }else{
                    authFlag = authFlag + BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG;
                }
                authResources.add(authFlag);
            });
        }));
        authResources.forEach( authResource -> {
            auths.add(new SimpleGrantedAuthority(authResource));
        });
        sysUser.setAuthorities(auths);
    }
}
