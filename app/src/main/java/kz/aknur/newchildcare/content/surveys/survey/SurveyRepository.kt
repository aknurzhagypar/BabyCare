package kz.aknur.newchildcare.content.surveys.survey

import android.app.Application
import android.content.Context
import com.google.gson.JsonArray
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.surveys.models.SurveyStartModel

class SurveyRepository(application: Application) {

    companion object {
        const val TAG = "SurveyRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)



    suspend fun startSurvey(month: Int, groupId: Int): SurveyStartModel?{
        val result = networkService.startSurvey(month, groupId, "Bearer " + userSession.getAccessToken().toString())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }

    suspend fun sendAnswer(surveyId: Int,answerList: JsonArray): Boolean{
        val result = networkService.sendAnswer(surveyId, answerList, "Bearer " + userSession.getAccessToken().toString())
        return result.code() == 200
    }


}