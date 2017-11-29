package com.gogh.glauncher.bean.daily

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/28/2017 do fisrt create. </li>
 */
data class MainBean(var temp: Double, var temp_min: Double, var temp_max: Double, var pressure: Double,
                    var sea_level: Double, var grnd_level: Double, var humidity: Int, var temp_kf: Double)