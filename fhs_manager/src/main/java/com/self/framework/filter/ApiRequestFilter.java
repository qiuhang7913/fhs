package com.self.framework.filter;
import com.alibaba.fastjson.JSON;
import com.self.framework.base.BaseFrom;
import com.self.framework.base.BaseVo;
import com.self.framework.utils.ObjectCheckUtil;
import org.bouncycastle.jce.provider.symmetric.ARC4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @des: api拦截器
 * @author qiuhang
 * @version v1.0
 */
public class ApiRequestFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html; charset=utf-8");
        String accessToken = ((HttpServletRequest) servletRequest).getHeader("accessToken");
        if (!"AAAAAAAABBBBBBBCCCCCFFFF".equals(accessToken)){
            logger.error("accessToken 异常!");
            PrintWriter writer = servletResponse.getWriter();
            BaseVo baseVo = new BaseVo();
            baseVo.setErrorMsg("accessToken 异常");
            baseVo.setErrorCode(1001);
            writer.println(JSON.toJSONString(baseVo));
            writer.flush();
            writer.close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 获取body数据
     * @param inputStream
     * @return
     */
    private String getRequestBody(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            if (inputStream != null) {
                 bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                 char[] charBuffer = new char[128];
                 int bytesRead = -1;
                 while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                     stringBuilder.append(charBuffer, 0, bytesRead);
                 }
            }
        }catch (Exception e){
            logger.error("异常!", e);
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    logger.error("异常!", ex);
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {

    }
}
