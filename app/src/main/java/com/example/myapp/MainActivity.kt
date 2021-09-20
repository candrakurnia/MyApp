package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.details.ForecastDetailsActivity

class MainActivity : AppCompatActivity() {

    private  val forecastRepository = ForecastRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEDT :EditText = findViewById(R.id.edt_zipcode)
        val enter : Button = findViewById(R.id.button)

        enter.setOnClickListener {
            val zipcode : String = zipcodeEDT.text.toString()

            if (zipcode.length != 5) {
                Toast.makeText(this,"pls enter valid zipcode",Toast.LENGTH_SHORT).show()
            }
            else {
                forecastRepository.loadForecast(zipcode)
            }
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter() {
            val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
            startActivity(forecastDetailsIntent)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            // update our list adapter
            dailyForecastAdapter.submitList(forecastItems)

        }
        forecastRepository.weeklyForecast.observe(this,weeklyForecastObserver)
    }
}