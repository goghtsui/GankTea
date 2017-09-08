package com.gogh.fortest.math;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 3/2/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/2/2017 do fisrt create. </li>
 */
public class Arithmetic {

    private int arrays[]={
            49, 38, 65, 97, 76, 13, 27,
            49, 78, 34, 12, 64, 5, 4,
            62, 99, 98, 54 ,56, 17, 18,
            23, 34, 15, 35, 25, 53, 51};

    public void insert(){
        int temp=0;
        for(int i=1;i<arrays.length;i++){
            int j=i-1;
            temp=arrays[i];
            for(;j>=0&&temp<arrays[j];j--){
                arrays[j+1]=arrays[j];  //将大于temp的值整体后移一个单位
            }
            arrays[j+1]=temp;

            for(int in=0;in<arrays.length;in++){
                System.out.print( " " + arrays[in]);
            }
            System.out.println("\n");
        }
    }

}
