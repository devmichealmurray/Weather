package com.devmmurray.weather.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devmmurray.weather.R
import com.devmmurray.weather.ui.viewmodel.MainActivityViewModel


private const val TAG = "* * * MAIN ACTIVITY * * *"
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
Log.d(TAG, "* * On Create Called * * ")
        mainViewModel.getWeather(32.755489, -97.330765,"imperial")

    }
}