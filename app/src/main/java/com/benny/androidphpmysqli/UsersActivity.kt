package com.benny.androidphpmysqli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val recyclerUsers: RecyclerView = findViewById(R.id.recyclerUsers)
        val usersList = ArrayList<User>()
        val layoutManager = LinearLayoutManager(this)
        recyclerUsers.layoutManager = layoutManager

        //Divider
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        recyclerUsers.addItemDecoration(divider)

        //adapter
        val adapter = CustomAdapter(usersList)
        recyclerUsers.adapter = adapter

        //Fetch Data
        val queue = Volley.newRequestQueue(this)
        val url = "https://android.emobilis.ac.ke/fetch.php"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val jsonArray = response.getJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                val userObject = jsonArray.getJSONObject(i)
                val id = userObject.get("id").toString()
                val name = userObject.get("name").toString()
                val email = userObject.get("email").toString()
                val phone = userObject.get("phone").toString()
                val address = userObject.get("address").toString()
                val city = userObject.get("city").toString()
                val country = userObject.get("country").toString()
                val person = User(id, name, email, phone, address, city, country)
                usersList.add(person)


            }//Fetching users
            adapter.notifyDataSetChanged()
        }, {
            Toast.makeText(this, "An error occurred!", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)

    }
}