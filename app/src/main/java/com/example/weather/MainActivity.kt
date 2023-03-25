package com.example.weather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val lat = intent.getStringExtra("lat")
        var long = intent.getStringExtra("long")

        getJsonData(lat,long)

    }

    private fun getJsonData(lat:String?,long:String?) {
        val API_KEY = "46588d94aa6aa276828fb62099e37ba4"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
         val jsonRequest =JsonObjectRequest(
             Request.Method.GET,url,null,
             Response.Listener{
                 response ->
                 Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()

             },Response.ErrorListener { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)

    }
}