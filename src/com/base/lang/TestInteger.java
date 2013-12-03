package com.base.lang;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestInteger {
    public static void main(String[] args) {
        Random random = new Random();
        List<Integer> list = new ArrayList<Integer>();

        // 使用BigInteger 作为 id 集合
        BigInteger ids = BigInteger.ZERO;
        for (int i = 0; i < 10; i++) {
            int id = random.nextInt(10);
            list.add(id);

            // 将id放入BigInteger
           ids = ids.or(BigInteger.ZERO.setBit(id));
        }

        // 判断id是否存在
        for (Integer id : list) {
            System.out.println("Collection.contains( "+id+" )= " + ids.testBit(id));
        }
    }
}