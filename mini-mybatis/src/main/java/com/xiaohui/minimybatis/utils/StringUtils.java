package com.xiaohui.minimybatis.utils;

/**
 * @Author: xiaohui
 * @Description: 做数据库字段和PO对象转化时的工具类
 */
public class StringUtils {

    public static String replaceAllChar(String s, char fromChar, char toChar) {
        char[] chars = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            char c = chars[i];
            if (fromChar == c) {
                chars[i] = toChar;
            }

        }
        return new String(chars);
    }

    /**
     * 首字母小写
     */
    public static String firstCharSwapCase(String s) {
        if (s != null && s.length() > 0) {
            char[] chars = s.toCharArray();
            char c = chars[0];
            if (c >= 'A' && c <= 'Z') {
                c += 32;
                chars[0] = c;
                return new String(chars);
            }
        }
        return s;
    }

    /**
     * 首字母大写
     */
    public static String firstCharSwapHigh(String s) {
        if (s != null && s.length() > 0) {
            char[] chars = s.toCharArray();
            char c = chars[0];
            if (c >= 'a' && c <= 'z') {
                c -= 32;
                chars[0] = c;
                return new String(chars);
            }
        }
        return s;
    }

}
