package com.lb.test.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liub on 2017/2/8.
 */
public class PremSize {

    public static void main1(String[] args)  {
        Random rnd = new Random();
        List<String> interned = new ArrayList<String>();
        for (; ;) {
            int length = rnd.nextInt(100);
            StringBuilder builder = new StringBuilder();
            String chars = "abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < length; i++) {
                builder.append(chars.charAt(rnd.nextInt(chars.length())));
            }
            interned.add(builder.toString().intern());
        }
    }

    public static void main(String[] args) {
        List<String> all = new ArrayList<String>();
        int i  = 0;
        while(true){
            all.add(String.valueOf(i++).intern());
        }
    }
}