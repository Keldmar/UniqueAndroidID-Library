package com.keldmar.uniqueid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.keldmar.uniqueidlibrary.UniqueID


class MainActivity : AppCompatActivity() {

    private val TAG = "UniqueID"

    val uniqueID = UniqueID()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE),
                100);
            return
        } else {

            val telephonyManager: TelephonyManager
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.deviceId
            Log.d(TAG, "telephonyManager.deviceId ${telephonyManager.deviceId}")
        }

        Log.d(TAG, "uniqueID.getSecureAndroidID(contentResolver) ${uniqueID.getSecureAndroidID(contentResolver)}")


        Log.d(TAG, "uniqueID.getMacAddress(application) ${uniqueID.getMacAddress(application)}")


        Log.d(TAG, "uniqueID.getSerialNumber() ${uniqueID.getSerialNumber()}")


        Log.d(TAG, "uniqueID.useUUID(this) ${uniqueID.useUUID(this)}")
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            // Permission is granted. Continue the action or workflow
            // in your app.

            val telephonyManager: TelephonyManager
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.deviceId
        }
    }
}