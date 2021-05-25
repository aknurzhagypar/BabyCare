package kz.aknur.newchildcare.content.child.list

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import retrofit2.Response

class ChildListRepository(application: Application) {

    companion object {
        const val TAG = "ChildListRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)


    suspend fun getMyChild(): Response<ChildListResponse> {
        return networkService.getMyChild("Bearer " + userSession.getAccessToken().toString())
    }



}

