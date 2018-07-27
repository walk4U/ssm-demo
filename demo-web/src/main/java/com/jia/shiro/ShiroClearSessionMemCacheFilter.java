package com.jia.shiro;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
public class ShiroClearSessionMemCacheFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ShiroClearSessionMemCacheFilter.class);

    @Resource
    private ShiroCacheManager cacheManager;

    private String sessionIdName;


    public void setSessionIdName(String sessionIdName) {
        this.sessionIdName = sessionIdName;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            String sessionId = null;
            for (Cookie cookie: cookies) {
                if (StringUtils.equals(sessionIdName, cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
            if (sessionId != null) {
                ShiroValueCache targetCache = cacheManager.getTargetCache(cacheManager.getActiveSessionsCacheName());
                if (targetCache != null) {
                    targetCache.clearMemoryCache(sessionId);
                }else {
                    logger.error("Target cache is null:" +sessionId +  "," + cacheManager.getActiveSessionsCacheName());
                }

            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
