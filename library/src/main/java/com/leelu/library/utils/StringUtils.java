package com.leelu.library.utils;

/**
 * @author : leelu
 * CreateTime : 2018/8/7 19:22
 * Description : 字符串工具类
 * isEmpty         : 判断字符串是否为 null 或长度为 0
 * isTrimEmpty     : 判断字符串是否为 null 或全为空格
 * isSpace         : 判断字符串是否为 null 或全为空白字符
 * equals          : 判断两字符串是否相等
 * equalsIgnoreCase: 判断两字符串忽略大小写是否相等
 * null2Length0    : null 转为长度为 0 的字符串
 * length          : 返回字符串长度
 * upperFirstLetter: 首字母大写
 * lowerFirstLetter: 首字母小写
 * reverse         : 反转字符串
 * toDBC           : 转化为半角字符
 * toSBC           : 转化为全角字符
 */
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s 字符串
     * @return 为null或长度为则为true，否则为false
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 字符串
     * @return 为 null 或全为空格则为true
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 字符串
     * @return 为 null 或全为空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 两字符串相等，则为true，不相等则为false
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 两字符串忽略大小写的情况下相等，则为true，不相等则为false
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 将 null 转为长度为 0 的字符串
     *
     * @param s 字符串
     * @return 如果为 null ，则转为长度为的字符串
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return 字符串长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 字符串
     * @return 将字符串首字母大写
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 字符串
     * @return 将字符串首字母小写
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 字符串
     * @return 反转后的字符串
     */
    public static String reverse(final String s) {
        if (s == null) return "";
        int len = s.length();
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 字符串
     * @return 将字符串中每个字符转为半角字符
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转为全角字符
     *
     * @param s 字符串
     * @return 将字符串中每个字符转为全角字符
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 字符串数组转为字符串
     *
     * @param array 字符床
     * @param regex 分割点
     * @return 返回拼接好的字符串
     */
    public static String array2String(String[] array, String regex) {
        if (array.length == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                sb.append(array[i]);
            } else {
                sb.append(array[i]);
                sb.append(regex);
            }
        }
        return sb.toString();
    }

    /**
     * @param str 要转换的字符串
     * @param regex 分割符号
     * @return 分割后的字符串数组
     */
    public static String[] string2Array(String str,String regex){
        if (StringUtils.isEmpty(str)){
            return new String[0];
        }
        return str.split(regex);
    }
}
