package com.process.mobike.utils;

/**
 * @author Lynn
 * @since 2019-04-19
 */
import java.security.MessageDigest;


public class MD5andKL {

    // MD5加码。32位
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    // 可逆的加密算法
    public static String KL(String inStr) {
        // String s = new String(inStr);
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    // 测试主函数
    public static void main(String args[]) {
        String s = "866262034467562,866262034473396,866262034552181,866262034694660,866262034474949,866262034718337,866262034687581,866262034587450,866262034697853,866262035523090,866262035509602,866262035522977,866262035577310,866262035636355,866262035586584,866262035586592,866262035630309,866262035527158";
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + MD5(s));
        System.out.println("MD5后再加密：" + KL(MD5(s)));
        System.out.println("解密为MD5后的：" + KL(KL(MD5(s))));
    }

}
