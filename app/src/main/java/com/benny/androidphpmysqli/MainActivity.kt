package com.benny.androidphpmysqli

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName: EditText = findViewById(R.id.inputName)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputPhone: EditText = findViewById(R.id.inputPhone)
        val inputAddress: EditText = findViewById(R.id.inputAddress)
        val inputCity: EditText = findViewById(R.id.inputCity)
        val inputCountry: EditText = findViewById(R.id.inputCountry)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonFetch: Button = findViewById(R.id.buttonFetch)

        val queue = Volley.newRequestQueue(this)
        val url = "https://android.emobilis.ac.ke/insert.php"

        buttonSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val phone = inputPhone.text.toString().trim()
            val address = inputAddress.text.toString().trim()
            val city = inputCity.text.toString().trim()
            val country = inputCountry.text.toString().trim()
            //Regex
            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty()) {
                //Save
                val progressDialog = ProgressDialog(this)//Creating a progress bar
                progressDialog.setTitle("Saving...")
                progressDialog.setMessage("Processing")
                progressDialog.show()
                val request = object : StringRequest(Method.POST, url, {
                    //TODO use a bottomsheatDialog
                    progressDialog.dismiss()
                    //Response from Server
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                    //Clearing Input
                    inputName.text.clear()
                    inputEmail.text.clear()
                    inputPhone.text.clear()
                    inputAddress.text.clear()
                    inputCity.text.clear()
                    inputCountry.text.clear()
                }, {
                    progressDialog.dismiss()
                    Log.e("SAVING", "onCreate: ", it)
                    Toast.makeText(this, "Error occurred while saving user", Toast.LENGTH_SHORT)
                        .show()
                })/*Requesting data*/ {
                    override fun getParams(): MutableMap<String, String>? {
                        val map = HashMap<String, String>()
                        map["name"] = name
                        map["email"] = email
                        map["phone"] = phone
                        map["address"] = address
                        map["city"] = city
                        map["country"] = country

                        return map
                    }
                }//Sending data to the server
                queue.add(request)

            } else {
                Toast.makeText(this, "Fill in all details!", Toast.LENGTH_SHORT).show()
            }


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
            /* val weatherUrl = "https://api.weatherapi.com/v1/current.json?key=f269d6ac5ca5477896375924220208&g=Mombasa"
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
             queue.add(weatherRequest)*/


        }

        buttonFetch.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
    }
}