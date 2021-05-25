package kz.aknur.newchildcare.signUp.firstPage

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.signUp.firstPage.models.SignUpRequest
import kz.aknur.newchildcare.signUp.firstPage.models.UserDetailModel
import org.json.JSONObject


class SignUpRepository(application: Application) {

    companion object {
        const val TAG = "SignUpRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    suspend fun signUp(signUpRequest: SignUpRequest): String {
        val response = networkService.signUp(signUpRequest)
        return if (response.isSuccessful) {
            val user = response.body()!!.data.user
            val token = response.body()!!.data.token
            if (saveUserInfo(
                    token, user
                )) {
                user.id.toString()
            } else "null"
        } else {
            val errorBodyJson = JSONObject(response.errorBody()!!.string())
            errorBodyJson.getString("error_description")
        }
    }

    private fun saveUserInfo(
        accessToken: String,
        user: UserDetailModel
    ): Boolean {
        userSession.setAccessToken(accessToken)
        userSession.setIsAuthorize(true)
        userSession.setId(user.id)
        userSession.setEmail(user.email)
        userSession.setPhone(user.phone)
        userSession.setName(user.name)
        userSession.setGender(user.gender)
        userSession.setAddress(user.address)
        userSession.setCityId(user.cityId)
        userSession.setPolyclinicId(user.polyclinicId)
        userSession.setIsPregnant(user.isPregnant)
        userSession.setPregnancyWeekCount(user.pregnancyWeekCount)
        return true
    }

}