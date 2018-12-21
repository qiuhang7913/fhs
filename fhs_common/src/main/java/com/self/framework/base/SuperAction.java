package com.self.framework.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @des 基础action
 * @author qiuhang
 * @version v1.0
 */
public class SuperAction {

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String goPage(HttpServletRequest request, HttpServletResponse response){
        String goWhere = request.getRequestURI();
        return goWhere;
    }
}
