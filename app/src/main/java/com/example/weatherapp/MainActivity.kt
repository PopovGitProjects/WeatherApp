package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.constants.Constant
import com.example.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getResult("Perm'")

        binding.btn.setOnClickListener {
            getResult("Perm'")
        }
    }
    private fun getResult(name: String){
        val url = "https://api.weatherapi.com/v1/current.json" +
                "?key=${Constant.KEY}&q=$name&aqi=no"
        val queue =  Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current").getString("temp_c")
                val wind = obj.getJSONObject("current").getString("wind_kph")
                val pressure = obj.getJSONObject("current").getString("pressure_mb")
                val windDirection = obj.getJSONObject("current").getString("wind_dir")

                setResponse(temp, wind, windDirection, pressure)
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
    private fun setResponse(temperature: String,
                            windSpeed: String,
                            windDirection: String,
                            pressure: String

    ){
        binding.apply {
            tvTemp.text = temperature
            tvWS.text = windSpeed
            tvWindDir.text = windDirection
            tvPres.text = pressure
        }
    }
}