package kz.aknur.newchildcare.content.child.add

import android.app.Application
import android.content.Context
import android.util.Log
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.child.add.models.ChildAddRequest

class ChildAddRepository(application: Application) {

    companion object {
        const val TAG = "ChildAddRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)


    suspend fun addChild(childAddRequest: ChildAddRequest): Boolean {
        Log.d(TAG, childAddRequest.toString())
        val request = networkService.addNewChild(
            "Bearer " + userSession.getAccessToken().toString(),
            childAddRequest
        )
        return request.code()==200
    }



}

