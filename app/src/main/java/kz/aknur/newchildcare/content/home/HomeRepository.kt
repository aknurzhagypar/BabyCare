package kz.aknur.newchildcare.content.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.home.categories.models.CategoriesModel

class HomeRepository(application: Application) {

    companion object {
        const val TAG = "HomeRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    suspend fun getMainCategories() : CategoriesModel?{
        val result = networkService.getMainCategories("Bearer "+ userSession.getAccessToken())
        return if (result.body()?.status == "success"){
            result.body()!!.data
        } else {
            null
        }
    }

    fun getName(): String {
        return userSession.getName().toString()
    }

    @SuppressLint("LongLogTag")
    suspend fun getCity(): String? {
        val cities = networkService.getCities("Bearer "+ userSession.getAccessToken()).body()!!.data
        Log.d(TAG, cities.toString())
        for (i in cities.indices){
            Log.d(TAG, cities[i].id.toString() + " + " + cities[i].name + " userCityId: " + userSession.getCityId()!!.toString())
            if (cities[i].id == userSession.getCityId()!!.toInt()){
                return cities[i].name
                break
            }
        }
        return null
    }

}