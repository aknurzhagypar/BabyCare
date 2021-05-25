package kz.aknur.newchildcare.content.profile

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import kz.aknur.newchildcare.content.profile.models.ProfileInfoModel

class ProfileRepository(application: Application) {

    companion object {
        const val TAG = "ProfileRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)


    fun logOut(): Boolean {
        userSession.clear()
        return true
    }

    suspend fun getCardCategories() : List<SmallCategoriesModel>?{
        val result = networkService.getCardCategories("Bearer "+ userSession.getAccessToken())
        return if (result.body()?.status == "success"){
            result.body()!!.data
        } else {
            null
        }
    }

    suspend fun getProfileInfo(): ProfileInfoModel?{
        val result = networkService.getProfileInfo("Bearer "+ userSession.getAccessToken())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }


}