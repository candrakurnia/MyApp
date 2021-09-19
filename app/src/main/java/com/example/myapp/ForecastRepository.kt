package com.example.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String) {
        val randomValues = List(10) { Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map{temp ->
            DailyForecast(temp,getTempDescription(temp))
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getTempDescription(temp:Float) : String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "anything below 0 doesnt make sense"
            in 0f.rangeTo(32f) ->"way to cold"
            else -> "doesnt compute12345"
        }
    }
}