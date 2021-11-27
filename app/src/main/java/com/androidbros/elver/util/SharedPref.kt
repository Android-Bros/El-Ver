package com.androidbros.elver.util

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    internal var mySharedPref: SharedPreferences =
        context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

    fun setOnBoardingState(state: Boolean?) {
        val editor = mySharedPref.edit()
        editor.putBoolean("isFinished", state!!)
        editor.apply()
    }

    fun loadOnBoardingState(): Boolean {
        return mySharedPref.getBoolean("isFinished", false)
    }


    fun setCurrentLocation(latlon: String) {
        val editor = mySharedPref.edit()
        editor.putString("currentLocation", latlon)
        editor.apply()
    }

    fun getCurrentLocation(): String? {
        return mySharedPref.getString("currentLocation", "null")
    }

}