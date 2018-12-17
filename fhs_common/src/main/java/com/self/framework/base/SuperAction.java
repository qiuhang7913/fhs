package com.self.framework.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class SuperAction {

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String goPage(HttpServletRequest request){
        String goWhere = request.getRequestURI();
        return goWhere;
    }
}
