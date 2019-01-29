package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;
import com.self.framework.ucenter.dao.MenuFuncDao;
import com.self.framework.ucenter.service.MenuFuncService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class MenuFuncServiceImpl extends BaseServiceImpl<SysMenuResourceFunc> implements MenuFuncService {

    @Autowired
    private MenuFuncDao menuFuncDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addAll(String menuId) {
        List<SysMenuResourceFunc> sysMenuResourceFuncs = Arrays.asList(
                SysMenuResourceFunc.builder().
                        menuId(menuId).
                        funcName(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_FLAG).
                        funcType(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_ADD_CODE).
                        funcDes("新增操作!").build(),
                SysMenuResourceFunc.builder().
                        menuId(menuId).
                        funcName(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_FLAG).
                        funcType(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_UPDATE_CODE).
                        funcDes("修改操作!").build(),
                SysMenuResourceFunc.builder().
                        menuId(menuId).
                        funcName(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_FLAG).
                        funcType(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_DELETE_CODE).
                        funcDes("删除操作!").build(),
                SysMenuResourceFunc.builder().
                        menuId(menuId).
                        funcName(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_FLAG).
                        funcType(BusinessCommonConstamt.SYS_MENU_RESOURCE_FUNC_FIND_CODE).
                        funcDes("查询操作!").build()
        );
        Integer rvCode = 0;
        for (SysMenuResourceFunc sysMenuResourceFunc:sysMenuResourceFuncs) {
            rvCode = this.addOrUpdata(sysMenuResourceFunc);
        }
        return rvCode;
    }
}
