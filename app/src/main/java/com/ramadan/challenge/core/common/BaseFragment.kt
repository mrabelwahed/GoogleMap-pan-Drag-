package com.ramadan.challenge.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import com.ramadan.challenge.R
import com.ramadan.challenge.feature.restaurants.HomeActivity

open class BaseFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLocationCallback: LocationCallback? = null
    private var mLocationRequest: LocationRequest? = null

    companion object {
        const val GOOGLE_PLAY_SERVICES_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupApiClient()
    }

    private fun setupApiClient() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(getRootActivity())
    }

    fun getUserLocation(onLocationAvailable: (Location) -> Unit) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null)
                        onLocationAvailable(location)
                    else
                        createLocationRequest(onLocationAvailable)
                }
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun createLocationRequest(onLocationAvailable: (Location) -> Unit) {
        mLocationRequest = LocationRequest.create()
        mLocationRequest?.interval = 5000.toLong()
        mLocationRequest?.fastestInterval = 5000.toLong()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                for (location in locationResult!!.locations) {
                    onLocationAvailable(location)
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(activity?.applicationContext)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, GOOGLE_PLAY_SERVICES_REQUEST)
            } else {
                activity?.finish()
            }
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (!checkPlayServices()) {
            Toast.makeText(
                activity?.applicationContext,
                getString(R.string.google_play_services_alert),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun stopLocationUpdates() {
        if (fusedLocationClient != null && mLocationCallback != null)
            fusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    fun getRootActivity(): HomeActivity = activity as HomeActivity

    // method to check
    // if location is enabled
    open fun isLocationEnabled(): Boolean {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
