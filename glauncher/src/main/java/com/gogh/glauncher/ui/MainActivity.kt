package com.gogh.glauncher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gogh.glauncher.R
import com.gogh.glauncher.model.WeatherModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WeatherModel().load("39.9075", "116.3972", WeatherModel.DAY)
        WeatherModel().load("39.9075", "116.3972", WeatherModel.DAILY)
        weather_json.text = "successfully"
    }
}
