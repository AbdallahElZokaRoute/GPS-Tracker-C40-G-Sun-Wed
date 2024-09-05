package com.route.gps_trackerc40gsunwed

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    val permissionLauncher =
        //      key    value
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true || it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                getUserLocation()
            } else {
                // Show dialog for explanation of feature disabled
                showDialog(
                    "Kindly Notice : This feature will be disabled",
                    "OK",
                    { dialog, which ->
                        dialog?.dismiss()
                    },
                    null,
                    null
                )
            }

        }
    var marker: Marker? = null
    var googleMap: GoogleMap? = null

    // Lazy initialization
    // Memory Consumption
    val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // UI Thread <-> Navigation and handle User Clicks - Show Dialogs
        if (isPermissionAllowed(Manifest.permission.ACCESS_FINE_LOCATION) ||
            isPermissionAllowed(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            // Access the location

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
            getUserLocation()
        } else {
            // Request The Permission
            requestPermissionFromUser()
        }
    }

    fun requestPermissionFromUser() {
        //   1st time ->
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) { //show explanation to the user why you need permission
            showDialog(
                "We need to access location because without location we Can't get nearest drivers",
                positiveButtonText = "I Understand",
                onPositiveClickListener = { dialog, which ->
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                    dialog?.dismiss()
                },
                "Cancel",
                onNegativeClickListener = { dialog, which ->
                    dialog?.dismiss()

                }
            )

        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            for (location in result.locations) {
                Log.e("TAG-update", "onLocationResult: ${location.latitude}")
                Log.e("TAG-update", "onLocationResult: ${location.longitude}")
                val latLng = LatLng(location.latitude, location.longitude)
                putMarkerOnMap(latLng)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation() {
        val client: SettingsClient = LocationServices.getSettingsClient(this)

        val locationRequest =
            LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 8_000)
                .build()

        val builder = LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            val currentLocationRequest = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it == null) return@addOnSuccessListener
                Log.e("TAG", "getUserLocation: ${it.latitude}")
                Log.e("TAG", "getUserLocation: ${it.longitude}")
                Log.e("TAG", "getUserLocation: Speed = ${it.speed}")
            }
            fusedLocationProviderClient.getCurrentLocation(currentLocationRequest, null)
                .addOnSuccessListener {
                    Log.e("TAG-Current", "getUserLocation: ${it.latitude}")
                    Log.e("TAG-Current", "getUserLocation: ${it.longitude}")
                }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback,
                Looper.getMainLooper()
            )
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@MainActivity,
                        1_000
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    fun putMarkerOnMap(latLng: LatLng) {
        if (marker == null) {
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title("Current Location")
            marker = googleMap?.addMarker(markerOptions)
        } else
            marker?.position = latLng
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))


    }

    fun isPermissionAllowed(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    // 1- Runtime Permissions -> Sensitive Data => Voice of user or Camera or User Location
    // 2- Access user Location
    // 3- Integrate with google maps

}


