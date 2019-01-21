package com.self.framework.http;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @des 自定义csrf处理
 * @author qh
 * @version v1.0
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    /** 允许的资源*/
    private List<String> allowRes;

    /** 动作限制*/
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        if (allowRes != null && allowRes.size() > 0) {
            String servletPath = httpServletRequest.getServletPath();
            for (String url : allowRes) {
                if (servletPath.contains(url)) {
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(httpServletRequest.getMethod()).matches();
    }

    public List<String> getAllowRes() {
        return allowRes;
    }

    public void setAllowRes(List<String> allowRes) {
        this.allowRes = allowRes;
    }
}
