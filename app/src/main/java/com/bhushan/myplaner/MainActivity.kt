package com.bhushan.myplaner

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Check User Permission
        checkUserPermission()
    }

    private fun checkUserPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //If permission is not grated ask for it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1333)

        } else{
           // If you already have permission
            getUserLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // If permission is granted
            getUserLocation()
        }else{
            Toast.makeText(this,"We must access your location",Toast.LENGTH_LONG).show()
        }
    }

    private fun getUserLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                //get user location
                userLocation= it

                // set current location temporarily
                userLocation.latitude = 57.581733
                userLocation.longitude = 12.092863

                //Also get the nearby stop info
                TODO()
                // viewModel.queryNearByStops(it)
            }
            .addOnFailureListener {
                Toast.makeText(this,"Failed to got location",Toast.LENGTH_LONG).show()
            }
    }
}
