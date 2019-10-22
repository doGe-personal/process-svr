package com.process.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author Danfeng
 * @since 2018/12/10
 */
public abstract class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);
    private static final String XSRF_NAME = "el-xsrf";
    private static final String XSRF_PARAM_NAME = "_xsrf_";
    private static final Cookie XSRF_COOKIE_NULL = new Cookie("el-xsrf", (String)null);

    public SecurityUtil() {
    }

    public static void createXsrfToken(HttpServletRequest request, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        Cookie tokenCookie = new Cookie("el-xsrf", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath(request.getContextPath() + "/");
        response.addCookie(tokenCookie);
        response.setHeader("el-result-code", token);
        log.trace("[CORE-XSRF] token created: {}@{}", tokenCookie.getValue(), tokenCookie.getPath());
    }

    public static boolean checkXsrfToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.trace("[CORE-XSRF] SHOULD fetch anti-CSRF token before {}", request.getRequestURI());
            return false;
        } else {
            Cookie tokenCookie = (Cookie) Arrays.stream(cookies).filter((cookie) -> {
                return cookie.getName().equals("el-xsrf");
            }).findAny().orElse(XSRF_COOKIE_NULL);
            if (tokenCookie == XSRF_COOKIE_NULL) {
                log.trace("[CORE-XSRF] no token in cookie - {}", request.getRequestURI());
                return false;
            } else {
                String tokenInCookie = tokenCookie.getValue();
                String tokenInRequest = request.getHeader("el-xsrf");
                if (tokenInRequest == null) {
                    tokenInRequest = request.getParameter("_xsrf_");
                    if (tokenInRequest == null) {
                        log.trace("[CORE-XSRF] no token in headers or parameters - {}", request.getRequestURI());
                        return false;
                    }
                }

                if (!tokenInRequest.equals(tokenInCookie)) {
                    log.trace("[CORE-XSRF] header({}) != cookie({})", tokenInRequest, tokenInCookie);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}
