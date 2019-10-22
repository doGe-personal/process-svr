package com.process.learn.utils;

/**
 * @author Lynn
 * @since 2019-04-19
 */

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 * @author xiangxg
 * aes 128加密器
 */

public class AES128 {

    /**
     * 加密
     *
     * @param content 需要加密的内容
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, genKey());// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解密
     *
     * @param content 待解密内容
     */
    public static String decrypt(String content) {

        try {
            byte[] decryptFrom = parseHexStr2Byte(content);
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, genKey());// 初始化
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据密钥获得 SecretKeySpec
     */
    private static SecretKeySpec genKey() {
        String strKey = "veir_123";// 加密解密密钥
        byte[] enCodeFormat = {0};

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            enCodeFormat = secretKey.getEncoded();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SecretKeySpec(enCodeFormat, "AES");
    }


    /**
     * 将二进制转换成16进制
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String hmacSha1(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String imeis = "866262034467562,866262034473396,866262034552181,866262034694660,866262034474949,866262034718337,866262034687581,866262034587450,866262034697853,866262035523090,866262035509602,866262035522977,866262035577310,866262035636355,866262035586584,866262035586592,866262035630309,866262035527158";
        System.out.println(hmacSha1(imeis,"866262034467562"));
    }

}