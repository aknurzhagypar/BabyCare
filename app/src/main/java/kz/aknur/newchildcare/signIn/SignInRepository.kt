package kz.aknur.newchildcare.signIn

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.signIn.models.AuthRequest
import kz.aknur.newchildcare.signUp.firstPage.models.UserDetailModel

class SignInRepository(application: Application) {

    companion object {
        const val TAG = "LoginRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    suspend fun logIn(authRequest: AuthRequest): Boolean {
        val response = networkService.signIn(authRequest)
        return if (response.body()?.status=="success") {
            if (saveUserToken(response.body()?.data?.token.toString())) {
                saveUserInfo(response.body()?.data!!.user)
            } else {
                userSession.clear()
                false
            }
        } else {
            userSession.clear()
            false
        }
    }

    private fun saveUserInfo(user: UserDetailModel): Boolean {
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

    private fun saveUserToken(accessToken: String): Boolean {
        userSession.setAccessToken(accessToken)
        userSession.setIsAuthorize(true)
        return true
    }

}