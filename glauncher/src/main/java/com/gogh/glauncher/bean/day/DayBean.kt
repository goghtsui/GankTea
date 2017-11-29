package com.gogh.glauncher.bean.day

import com.gogh.glauncher.bean.daily.CloudsBean
import com.gogh.glauncher.bean.daily.WeatherBean

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/28/2017 do fisrt create. </li>
 */
data class DayBean(var coord: CoordBean, var base: String, var main: MainBean,
                   var visibility: Int, var wind: WindBean, var clouds: CloudsBean, var dt: Int,
                   var sys: SysBean, var id: Int, var name: String?, var cod: Int,
                   var weather: List<WeatherBean>)