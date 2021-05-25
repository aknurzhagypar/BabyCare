package kz.aknur.newchildcare.splash

import android.app.Application
import android.content.Context
import android.util.Log
import kz.aknur.newchildcare.common.helpers.NetworkHelper
import kz.aknur.newchildcare.common.preferences.UserSession
import java.lang.Exception

class SplashRepository(application: Application) {

    companion object {
        const val TAG = "SplashRepository"
    }

    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    fun checkNetwork(context: Context): Boolean {
        return try {
            NetworkHelper.isNetworkConnected(context)
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            false
        }
    }

    fun checkAuthorize(): Boolean? {
        return try {
            userSession.getIsAuthorize()
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            false
        }
    }

}