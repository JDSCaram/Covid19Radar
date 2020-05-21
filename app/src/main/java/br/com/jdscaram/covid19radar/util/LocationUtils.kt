package br.com.jdscaram.covid19radar.util

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

class LocationUtils {
    companion object {
        private val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        }

        fun getMyLocation(locationManager: LocationManager?): Location? {
            if (locationManager == null) return null

            return try {
                var location: Location? = null
                val gpsEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val networkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (gpsEnabled) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        1f,
                        locationListener
                    )
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null && networkEnabled) {
                        location =
                            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    }
                } else if (networkEnabled) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0,
                        1f,
                        locationListener
                    )
                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }

                locationManager.removeUpdates(locationListener)

                return location
            } catch (exception: SecurityException) {
                Log.e("SecurityException", "${exception.message}", exception)
                null
            }
        }
    }
}