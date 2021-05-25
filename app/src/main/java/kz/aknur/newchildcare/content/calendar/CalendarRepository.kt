package kz.aknur.newchildcare.content.calendar

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.calendar.model.EventsResponse

class CalendarRepository(application: Application) {

    companion object {
        const val TAG = "CalendarRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    suspend fun getEvents(): List<EventsResponse.EventsModel>?{
        val result = networkService.getEvents("Bearer "+ userSession.getAccessToken())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }



}