package com.process.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Lynn
 * @since 2019-09-01
 */
public class BCryptUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean match(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }

    public static void main(String[] args) {
        String password = BCryptUtil.encode("password");
        System.out.println(password);
    }
}
