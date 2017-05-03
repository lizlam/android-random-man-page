package com.lizlam.randomman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by liz on 6/1/15.
 */
public class RandomManPage {

    public static String get(InputStream is) {
        String line;
        int count = 0;
        ArrayList<String> array = new ArrayList<>();
        BufferedReader bufferedReader;

        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            bufferedReader = new BufferedReader(isr);
            try {
                for (int i = 0; (line = bufferedReader.readLine()) != null; i++) {
                    array.add(i, line);
                    count = i;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }

        int index = (int) Math.ceil(Math.random() * count);
        return array.get(index);
    }

}
