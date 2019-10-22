package com.process.common.util;

/**
 * @author Danfeng
 * @since 2018/12/9
 */
public class DevError extends Error {
    private DevError(String message) {
        super(message);
    }

    private DevError(String message, Throwable cause) {
        super(message, cause);
    }

    public static DevError todo() {
        return new DevError("[TODO] Not impl.");
    }

    public static DevError unexpected() {
        return new DevError("[REDO] Unexpected.");
    }

    public static DevError unexpected(String tips) {
        return new DevError("[REDO] Unexpected: " + tips);
    }

    public static DevError unexpected(Throwable cause) {
        return new DevError("[REDO] Unexpected.", cause);
    }
}
