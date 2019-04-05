package com.self.framework.base;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.utils.ConvertDataUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @des 基础action
 * @author qiuhang
 * @version v1.0
 */
public class SuperAction {

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    protected ModelAndView goPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reqPath = request.getRequestURI();
        String goWhere = ConvertDataUtil.convertStr(request.getParameter("go"));
        if (goWhere.length() != BusinessCommonConstamt.ZERO_CODE){//未获取到所要前往的页面,则默认会跳地址
            goWhere = reqPath + "/" + goWhere;
        }else{
            goWhere = reqPath;
        }
        return new ModelAndView(goWhere.replaceFirst("/",""));
    }

    /**
     *
     * @return
     */
    protected UserDetails getSessionUserInfo(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
