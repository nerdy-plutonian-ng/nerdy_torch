package com.nerdymykl.nerdytorch

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TorchState : ViewModel() {

    private var cameraManager : CameraManager? = null
    private var cameraId : String? = null


    private var _isOn = MutableLiveData(false)
    val isOn: LiveData<Boolean>
        get() = _isOn

    fun clickSwitch(context: Activity){
        if(hasCameraPermission(context)){
            val newState = !_isOn.value!!
            if(cameraManager == null){
                cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            }
            if(cameraId == null){
                cameraId = cameraManager!!.cameraIdList[0] // Use the first camera
            }
            try {
                cameraManager!!.setTorchMode(cameraId!!, newState)
                _isOn.value = !_isOn.value!!
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context,"Enable camera permission in app settings",Toast.LENGTH_LONG).show()
        }


    }

    private fun hasCameraPermission(context: Activity) :  Boolean {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user, if needed
                // For example, you can display a dialog or a Snackbar explaining why the permission is required
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.CAMERA),
                    111
                )
            }
        } else {
            // Permission has already been granted
            // You can proceed with using the camera
            return  true
        }
        return  false
    }

    suspend fun getState(): Boolean = withContext(Dispatchers.Default) {
        delay(3000) // Delay for 3 seconds
        true // Return true
    }
}