package kz.aknur.newchildcare.content.home.articles.details

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.home.articles.list.AddNewFavArticleRequest
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel

class ArticleRepository(application: Application) {

    companion object {
        const val TAG = "ArticleRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)


    suspend fun getArticle(articleId: String): ArticlesModel?{
        val result = networkService.getArticle(articleId, "Bearer "+ userSession.getAccessToken())
        return if (result.code() == 200){
            result.body()!!.data
        } else null
    }

    suspend fun addToFav(articleId: AddNewFavArticleRequest): Boolean{
        return networkService.addNewFavArticle(articleId, "Bearer " + userSession.getAccessToken()!!).isSuccessful
    }

    suspend fun removeFromFav(articleId: String): Boolean{
        return networkService.removeFromFav(articleId, "Bearer " + userSession.getAccessToken()!!)?.isSuccessful!!
    }

}