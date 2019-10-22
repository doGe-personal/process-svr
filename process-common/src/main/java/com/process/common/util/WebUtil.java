package com.process.common.util;

import com.process.common.domain.OpResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Danfeng
 * @since 2018/12/12
 */
public abstract class WebUtil {

    private static final String HTTP_HEADER_ATTR = "PS-RESULT-CODE";

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String toTraceInfo(HttpServletRequest req) {
        return req.getRequestURI() + "?" + req.getQueryString() + " by u-" + req.getRemoteUser() + "@" + getClientIp(req);
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        return Optional.ofNullable(request.getCookies()).flatMap((cookies) -> {
            return Arrays.stream(cookies).filter((cookie) -> {
                return cookie.getName().equals(cookieName);
            }).findAny();
        });
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static boolean isReadRequest(HttpServletRequest request) {
        HttpMethod method = HttpMethod.resolve(request.getMethod());
        return method == HttpMethod.GET;
    }

    public static boolean isWriteRequest(HttpServletRequest request) {
        HttpMethod method = HttpMethod.resolve(request.getMethod());
        return method == HttpMethod.POST || method == HttpMethod.DELETE || method == HttpMethod.PUT || method == HttpMethod.PATCH;
    }

    public static boolean isFileRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && MediaType.valueOf(contentType).isCompatibleWith(MediaType.MULTIPART_FORM_DATA);
    }

    public static ResponseEntity.BodyBuilder opBuilder(OpResult result) {
        String resultCode = Optional.ofNullable(result).orElse(OpResult.OK).getCode();
        return ResponseEntity.ok().header(HTTP_HEADER_ATTR, resultCode);
    }

    public static ResponseEntity.BodyBuilder okBuilder() {
        return opBuilder(OpResult.OK);
    }

    public static <T> ResponseEntity<T> ok(T t) {
        return okBuilder().body(t);
    }

    public static ResponseEntity.BodyBuilder ngBuilder() {
        return opBuilder(OpResult.NG);
    }

    public static ResponseEntity<Object> toResponseEntity(OpResult result) {
        return opBuilder(result).build();
    }

    public static ResponseEntity<Object> toResponseEntity() {
        return okBuilder().build();
    }
}
