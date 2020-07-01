package com.keldmar.uniqueidlibrary

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import java.util.*


class UniqueID {

    private var uniqueID: String? = null
    private val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    fun getSecureAndroidID(contentResolver: ContentResolver): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }


    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    /** won't work on Marshmallow and above  */
    fun getMacAddress(application: Application): String {
        val manager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo;
        return (info.macAddress.toUpperCase())
    }

    @SuppressLint("MissingPermission")
    @Throws(SecurityException::class)
    @RequiresPermission(READ_PHONE_STATE)
    fun getSerialNumber(): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            throw SecurityException("Only system apps can require this permission")
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Build.getSerial()
            } else {
                /** for Android 7.1 and earlier */
                Build.SERIAL
            }
        }
    }

    fun useUUID(context: Context): String? {
        if (uniqueID == null) {
            val sharedPrefs = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE
            )
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.apply()
            }
        }
        return uniqueID
    }
}