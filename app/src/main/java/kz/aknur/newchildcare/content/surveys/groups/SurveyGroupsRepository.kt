package kz.aknur.newchildcare.content.surveys.groups

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.surveys.models.SurveyGroupsModel

class SurveyGroupsRepository(application: Application) {

    companion object {
        const val TAG = "SurveyGroupsRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)



    suspend fun getSurveyGroups(): List<SurveyGroupsModel>?{
        val result = networkService.getSurveyGroups("Bearer " + userSession.getAccessToken().toString())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }


}