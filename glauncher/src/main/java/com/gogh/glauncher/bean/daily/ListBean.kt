package com.gogh.glauncher.bean.daily

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/28/2017 do fisrt create. </li>
 */
data class ListBean(var dt: Int, var main: MainBean, var clouds: CloudsBean, var wind: WindBean,
                    var sys: SysBean, var dt_txt: String, var weather: List<WeatherBean>)