package com.surkhojb.architectmovies.common

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.surkhojb.architectmovies.MainApp
import com.surkhojb.data.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesDataSource: LocationDataSource {
    private val context = MainApp.getContext()
    private val geoCoder = Geocoder(context)
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): String? {
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result.getRegion())
            }
        }
    }

    private fun Location?.getRegion(): String? {
        val fromLocation = this?.let {
            geoCoder.getFromLocation(this.latitude,this.longitude,1)
        }
        return fromLocation?.firstOrNull()?.countryCode
    }
}