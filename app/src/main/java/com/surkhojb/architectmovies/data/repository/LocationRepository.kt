package com.surkhojb.architectmovies.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.surkhojb.architectmovies.MainApp
import com.surkhojb.architectmovies.common.PermissionManager
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume

class LocationRepository {
    private val DEFAULT_REGION = "US"
    private val context = MainApp.getContext()
    private val geoCoder =  Geocoder(context)
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)
    private val permissionManager = PermissionManager(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION))

    suspend fun findLastRegion(): String{
        val lastLocation = findLastLocation()
        return lastLocation.getRegion()
    }

    private suspend fun findLastLocation(): Location?{
        val success = permissionManager.requestPermissions()
        return if(success) getLocation() else null

    }

    @SuppressLint("MissingPermission")
    private suspend fun getLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }
    }

    private fun Location?.getRegion(): String {
        val fromLocation = this?.let {
            geoCoder.getFromLocation(this.latitude,this.longitude,1)
        }
        return fromLocation?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}