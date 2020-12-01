package com.surkhojb.architectmovies.common

import android.Manifest
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener
import com.surkhojb.architectmovies.MainApp
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionManager(var permissions: Collection<String> ){
    private val context = MainApp.getContext()

    suspend fun requestPermissions(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            Dexter.withContext(context)
                .withPermissions(permissions)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                       continuation.resume(report?.areAllPermissionsGranted() ?: false)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                       token?.continuePermissionRequest()
                    }
                }).check()
        }
    }

}