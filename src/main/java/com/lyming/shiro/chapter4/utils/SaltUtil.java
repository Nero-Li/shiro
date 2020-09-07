package com.lyming.shiro.chapter4.utils;

import java.util.Random;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-06 19:14
 */
public class SaltUtil {

    /**
     * 生成随机盐
     * @param n
     * @return
     */
    public static String getSalt(int n) {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-+=_".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char c = charArray[new Random().nextInt(charArray.length)];
            sb.append(c);
        }
        return sb.toString();
    }

}
