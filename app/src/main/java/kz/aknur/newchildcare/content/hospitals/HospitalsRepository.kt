package kz.aknur.newchildcare.content.hospitals

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.hospitals.models.HospitalModel

class HospitalsRepository(application: Application) {

    companion object {
        const val TAG = "HospitalsRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)



    suspend fun getHospitals(): List<HospitalModel>?{
        val result = networkService.getHospitals("Bearer " + userSession.getAccessToken().toString())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }


}