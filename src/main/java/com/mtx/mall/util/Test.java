package com.mtx.mall.util;

public class Test {
    public static void main(String[] args) {
        int min = 30;
        int max = 110;
        int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
        System.out.println("随机数为：" + randomNumber);
    }

}
