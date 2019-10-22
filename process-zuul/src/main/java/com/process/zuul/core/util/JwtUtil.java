package com.process.zuul.core.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * @author Danfeng
 * @since 2018/11/23
 */
public class JwtUtil {


    public static byte[] getHS256SecretBytes() {
        return TextCodec.BASE64.decode(MacProvider.generateKey(SignatureAlgorithm.HS256).toString());
    }

    public static byte[] getHS384SecretBytes() {
        return TextCodec.BASE64.decode(MacProvider.generateKey(SignatureAlgorithm.HS384).toString());
    }

    public static byte[] getHS512SecretBytes() {
        return TextCodec.BASE64.decode(MacProvider.generateKey(SignatureAlgorithm.HS384).toString());
    }

}
