package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SplashScreen : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var myRequestCode =1022

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        Handler(Looper.getMainLooper()).postDelayed({
//            var intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        },2000)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

    }
    // Location permission -->deny
    // Location denied through setting
    //Location permission le lo
    // GPS off


    private fun CheckPermission(): Boolean {
        if(
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false


    }

    private fun LocationEnable(): Boolean {
         var locationManager :LocationManager =getSystemService(Context.LOCATION_SERVICE ) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)




    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if(CheckPermission()){

            if(LocationEnable()) //gps off na ho
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    task ->
                    var location :Location = task.result
                    if(location==null){

                        NewLocation()

                    }else{
                        Log.i("Location",location.longitude.toString())
                    }

                }else{
                    Toast.makeText(this,"Please Turn on your GPS Location",Toast.LENGTH_LONG).show()
            }


        }else{
           RequestPermission()


        }

    }

    private fun NewLocation() {

    }


    private fun RequestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION ,
            android.Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)

    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode ==myRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }

}