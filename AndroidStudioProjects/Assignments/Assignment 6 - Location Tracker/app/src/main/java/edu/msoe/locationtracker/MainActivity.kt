package edu.msoe.locationtracker

import android.os.Bundle
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var locationTextView: TextView

    private lateinit var sensorManager: SensorManager
    private lateinit var lightSensor: Sensor
    private var isInPocket = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationTextView = findViewById(R.id.myTextView)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize the location request and callback
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 10000 // Location update interval in milliseconds (10 seconds)
        mLocationRequest.fastestInterval = 5000 // Fastest interval for location updates
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (isInPocket) {
                    stopLocationUpdates() // Stop updates if the phone is in the pocket
                }
                locationResult.locations.forEach { location: Location ->
                    Log.d("LocationUpdates", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                    locationTextView.text = "Updated Location: ${location.latitude}, ${location.longitude}"
                }
            }
        }

        // Initialize the sensor manager and light sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!!

        if (lightSensor == null) {
            // Light sensor not available on the device
            // Handle this case
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if not granted
            // Handle this case
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    override fun onResume() {
        super.onResume()
        // Register the light sensor listener
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        // Unregister the light sensor listener
        sensorManager.unregisterListener(this)
        stopLocationUpdates()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Check ambient light level to detect if the phone is in a pocket
            val lightLevel = event.values[0]
            isInPocket = lightLevel < 10
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes
    }
}
