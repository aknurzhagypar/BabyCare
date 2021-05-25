package kz.aknur.newchildcare.content.favorite_articles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.articles.list.AddNewFavArticleRequest
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import java.lang.Exception

class FavouriteArticlesViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "FavouriteArticlesViewModel"
    }

    private var repository: FavoriteArticlesRepository =
        FavoriteArticlesRepository(
            application
        )

    val categories: MutableLiveData<List<SmallCategoriesModel>> = MutableLiveData()
    val articles: MutableLiveData<List<ArticlesModel>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val isAdded: MutableLiveData<Boolean> = MutableLiveData()
    val isRemoved: MutableLiveData<Boolean> = MutableLiveData()


    fun getArticles(){
        viewModelScope.launch {
            try {
                articles.postValue(repository.getArticles())
            } catch (exception: Exception) {
                isError.postValue(exception.toString())
            }
        }
    }

    fun addToFavArticle(articleId: Int) {
        viewModelScope.launch {
            isAdded.postValue((repository.addToFav(AddNewFavArticleRequest(articleId = articleId))))
        }
    }

    fun removeFromArticle(articleId: Int) {
        viewModelScope.launch {
            isRemoved.postValue((repository.removeFromFav(articleId = articleId.toString())))
        }
    }
}