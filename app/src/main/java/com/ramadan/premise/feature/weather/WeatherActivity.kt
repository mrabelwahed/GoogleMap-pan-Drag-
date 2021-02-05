package com.ramadan.premise.feature.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramadan.premise.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }
}
