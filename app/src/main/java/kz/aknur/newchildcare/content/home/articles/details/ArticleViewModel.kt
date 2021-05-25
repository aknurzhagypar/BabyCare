package kz.aknur.newchildcare.content.home.articles.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.articles.list.AddNewFavArticleRequest
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import java.lang.Exception

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "ArticleViewModel"
    }


    private var repository: ArticleRepository =
        ArticleRepository(
            application
        )

    val article: MutableLiveData<ArticlesModel> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val isAdded: MutableLiveData<Boolean> = MutableLiveData()
    val isRemoved: MutableLiveData<Boolean> = MutableLiveData()

    fun getArticle(articleId: String) {
        viewModelScope.launch {
            try {
                article.postValue(repository.getArticle(articleId))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
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