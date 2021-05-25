package kz.aknur.newchildcare.signUp.secondPage

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.signUp.secondPage.models.CityModel
import kz.aknur.newchildcare.signUp.secondPage.models.OrganizationsModel
import kz.aknur.newchildcare.signUp.secondPage.models.PersonalInformationRequest

class PersonalInformationRepository(application: Application) {

    companion object {
        const val TAG = "PersonalInformationRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    @SuppressLint("LongLogTag")
    suspend fun getCities(): List<CityModel>? {
        val cities = networkService.getCities("Bearer " + userSession.getAccessToken().toString())
        return if (cities.code() == 200) {
            cities.body()?.data
        } else null
    }


    suspend fun sendUserInfo(user: PersonalInformationRequest): Boolean {
        val request = networkService.sendPersonalInfo(
            "Bearer " + userSession.getAccessToken().toString(),
            user
        )
        return if (request.code() == 200) {
            updateUserSession(user)
        } else {
            false
        }
    }

    private fun updateUserSession(user: PersonalInformationRequest): Boolean {
        return try {
            userSession.setPolyclinicId(user.polyclinicId)
            userSession.setCityId(user.cityId)
            userSession.setAddress(user.address)
            userSession.setIsPregnant(user.isPregnant)
            userSession.setPregnancyWeekCount(user.pregnancyWeekCount)
            true
        }catch (e: Exception){
            false
        }
    }


    @SuppressLint("LongLogTag")
    suspend fun getOrganizations(): List<OrganizationsModel>? {
        val organizations = networkService.getOrganizations("Bearer " + userSession.getAccessToken().toString())
        return if (organizations.code() == 200) {
            organizations.body()?.data
        } else null
    }


}

