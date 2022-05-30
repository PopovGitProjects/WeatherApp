package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject

const val KEY = "6cf520d6cc6f411587f110159223005"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            binding.tvTemp.text = getResult("London")
            Log.d("MyLog", getResult("London"))
        }
    }
    private fun getResult(name: String): String{
        val url = "https://api.weatherapi.com/v1/current.json" +
                "?key=$KEY&q=$name&aqi=no"
        var result = ""
        val queue =  Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current").getString("temp_c")
//                Log.d("MyLog", temp)
                result = temp
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
        return result
    }
}