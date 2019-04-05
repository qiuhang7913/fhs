package com.self.framework.ucenter.action;

import com.alibaba.fastjson.JSON;
import com.self.framework.base.SuperAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.SysRole;
import com.self.framework.ucenter.bean.SysUser;
import com.self.framework.ucenter.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 主页
 */
@Controller
@RequestMapping(value = "/index")
public class IndexAction extends SuperAction {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MenuService menuService;

    @Override
    protected ModelAndView goPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = BusinessCommonConstamt.DEFALUT_SUPER_MANAGER_LOGIN_NAME.equals(sysUser.getLoginName());
        List<SysMenuResource> currUserMenuRes = getCurrUserMenuRes(sysUser.getUserRoles(), isAdmin);
        request.setAttribute("currUserMenuRes",currUserMenuRes);
        return super.goPage(request, response);
    }
    /**
     * 获取当前用户菜单资源
     * @param userRoles
     * @return
     */
    private List<SysMenuResource> getCurrUserMenuRes(List<SysRole> userRoles, boolean isAdmin) {
        Set<SysMenuResource> sysMenuResourcesSet = new HashSet<>();
        if (isAdmin){//超级管理员不限制任何权限
            sysMenuResourcesSet.addAll(menuService.queryList(SysMenuResource.builder().status(BusinessCommonConstamt.ZERO_CODE).build()));
        }else {
            userRoles.forEach(userRole -> {
                sysMenuResourcesSet.addAll(userRole.getSysMenuResources());
            });
        }

        List<SysMenuResource> mapsRoot = new ArrayList<>();
        List<SysMenuResource> maps = new ArrayList<>();
        sysMenuResourcesSet.forEach(sysMenuResource -> {
            if ("0".equals(sysMenuResource.getParentId())){
                mapsRoot.add(sysMenuResource);//根
            }else{
                maps.add(sysMenuResource);//非根
            }
        });
        List<SysMenuResource> userSysMenuResources = mapsRoot.stream().sorted(Comparator.comparing(SysMenuResource::getSort)).collect(Collectors.toList());
        userSysMenuResources.forEach(userSysMenuResource -> {
            dealWhithRes(userSysMenuResource, maps);
        });
        logger.info("当前登陆用户菜单资源为[" + JSON.toJSONString(mapsRoot) + "]");
        return userSysMenuResources;
    }

    /**
     * 处理当前用户的菜单资源信息
     * @param root
     * @param maps
     */
    private void dealWhithRes(SysMenuResource root, List<SysMenuResource> maps){
        List<SysMenuResource> newMaps = new ArrayList<>();
        List<SysMenuResource> newNoMaps = new ArrayList<>();
        maps.forEach(map -> {
            if (map.getParentId().equals(root.getId())){
                newMaps.add(map);
            }else{
                newNoMaps.add(map);
            }
        });
        List<SysMenuResource> userSysMenuResources = newMaps.stream().sorted(Comparator.comparing(SysMenuResource::getSort)).collect(Collectors.toList());
        root.setChildren(userSysMenuResources);
        userSysMenuResources.forEach(map -> {
            dealWhithRes(map,newNoMaps);
        });
    }
}
