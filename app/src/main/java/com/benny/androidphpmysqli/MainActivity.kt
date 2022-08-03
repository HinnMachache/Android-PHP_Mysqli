package com.benny.androidphpmysqli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        /*val url = "https://android.emobilis.ac.ke/fetch.php"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { responseJson ->
            Log.d("FETCHING", "onCreate: $responseJson")
            val jsonArray = responseJson.getJSONArray("users")
            for (i in 0 until jsonArray.length())
            {
                val userJsonObject = jsonArray.getJSONObject(1)
                val name = userJsonObject.get("name")
                val phone = userJsonObject.get("phone")
                Log.d("USER", "onCreate: $name : $phone")
            }

        },
            { error ->
            Log.e("FETCHING", "onCreate: Error while creating", error)
        })
        queue.add(request)*/

        val weatherUrl = "https://api.weatherapi.com/v1/current.json?key=f269d6ac5ca5477896375924220208&g=Mombasa"
        val weatherRequest = JsonObjectRequest(Request.Method.GET, weatherUrl, null,
            {
            mainJsonObject ->
                val locationObject = mainJsonObject.getJSONObject("location")
                val city = locationObject.get("name")
                val country = locationObject.get("Country")
                Log.d("WEATHER", "onCreate: $city in $country")
                //Parse json
                val currentObject = mainJsonObject.getJSONObject("current")
                val temp = currentObject.get("temp_c")
                val windSpeed = currentObject.get("wind_kph")
                val visibility = currentObject.get("vis_ke")
                Log.d("WEATHER", "onCreate: Temperature is $temp C, wind speed is $windSpeed/kmh, visibility is $visibility km")

                val condition = mainJsonObject.getJSONObject("current").getJSONObject("condition").get("text")
                Log.d("WEATHER", "onCreate: Condition is $condition")
            },
            {
                error ->
                Log.e("WEATHER", "onCreate: Error while fetching weather data", error)
            })
        queue.add(weatherRequest)
    }
}