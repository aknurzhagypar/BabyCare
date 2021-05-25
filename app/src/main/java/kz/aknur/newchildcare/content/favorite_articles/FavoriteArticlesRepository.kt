package kz.aknur.newchildcare.content.favorite_articles

import android.app.Application
import android.content.Context
import kz.aknur.newchildcare.common.preferences.UserSession
import kz.aknur.newchildcare.common.remote.ApiConstants
import kz.aknur.newchildcare.common.remote.Networking
import kz.aknur.newchildcare.content.home.articles.list.AddNewFavArticleRequest
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel

class FavoriteArticlesRepository(application: Application) {

    companion object {
        const val TAG = "FavoriteArticlesRepository"
    }

    private val networkService =
        Networking.create(ApiConstants.APP_BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("userSession", Context.MODE_PRIVATE)
    private var userSession: UserSession =
        UserSession(sharedPreferences)

    suspend fun getArticles(): List<ArticlesModel>?{
        val result = networkService.getFavouriteArticles("Bearer "+ userSession.getAccessToken())
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