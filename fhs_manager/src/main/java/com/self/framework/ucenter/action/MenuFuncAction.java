package com.self.framework.ucenter.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.framework.base.BaseAction;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.bean.SysMenuResourceFunc;
import com.self.framework.ucenter.service.MenuFuncService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/menuFunc")
public class MenuFuncAction extends BaseAction<SysMenuResourceFunc> {

    @Autowired
    private MenuFuncService menuFuncService;

    @RequestMapping(value = "addAll/{menuId}", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Map> addAll(@PathVariable(value = "menuId") String menuId){
        Integer rvCode = menuFuncService.addAll(menuId);
        return rvCode > BusinessCommonConstamt.ZERO_CODE ? HttpResult.okResult():HttpResult.errorResult();
    }

    @RequestMapping(value = "checkFuncName")
    @ResponseBody
    public Map<String, Boolean> checkFuncName(
            @RequestParam(value = "func_id", required = false) String funcId,
            @RequestParam(value = "func_name") String funcName){
        Map<String,Boolean> rvMap = new HashMap<>();

        SysMenuResourceFunc one = menuFuncService.findOne(SysMenuResourceFunc.builder().funcName(funcName).build());
        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            rvMap.put("valid", true);
        }else{
            if (!ObjectCheckUtil.checkIsNullOrEmpty(funcId)){
                if (one.getId().equals(funcId)){//说明是自己
                    rvMap.put("valid", true);
                }else{
                    rvMap.put("valid", false);
                }
            }
        }

        return rvMap;
    }
}
