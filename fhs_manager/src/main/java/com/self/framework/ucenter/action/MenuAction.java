package com.self.framework.ucenter.action;

import com.self.framework.base.BaseAction;
import com.self.framework.constant.HttpCodeConstant;
import com.self.framework.http.HttpResult;
import com.self.framework.otherBean.TreeNode;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/sys/menu")
public class MenuAction extends BaseAction<SysMenuResource> {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "obtainTreeData", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<List<TreeNode>> obtainTreeData(HttpServletRequest request){
        List<TreeNode> treeNode = menuService.findMenuTreeData();
        return HttpResult.aOtherResult(HttpCodeConstant.HTTP_OK_CODE, HttpCodeConstant.HTTP_OK_CODE_DESCRIBE, treeNode);
    }
}
