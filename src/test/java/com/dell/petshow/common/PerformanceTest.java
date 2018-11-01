/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common;

import java.util.*;

/**
 * @author map6
 */
public class PerformanceTest {

    public static int[] timeLine = new int[30*1440];//43320

    public static Integer countRunningTime(List<Map<String,String>> data){
        for(int i = 0;i<data.size();i++){
            Map<String,String> temp = data.get(i);
            Integer start = Integer.valueOf(temp.get("start"));
            Integer end = Integer.valueOf(temp.get("end"));
            for(int j = start;j<end;j++){
                timeLine[j] = 1;
            }
        }
        int result = 0;
        for(int i=0;i<30*1440;i++){
            result += timeLine[i];
        }
        return result;
    }


    /**
     * @args command line arguments
     */
    public static void main(String[] args) {

        System.out.println(System.currentTimeMillis());
        List<Map<String,String>> testData = new ArrayList<>();

        for(int i=0;i<40;i++){
            Map<String,String> data = new HashMap<>();
            Integer start = i*100+20;
            Integer end = i*1000+30;
            data.put("start",start.toString());
            data.put("end",end.toString());
            testData.add(data);
        }
        System.out.println("Time running:" + countRunningTime(testData));
        System.out.println(System.currentTimeMillis());
    }

}
