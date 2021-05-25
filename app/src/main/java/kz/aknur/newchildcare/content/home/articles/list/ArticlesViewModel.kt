package kz.aknur.newchildcare.content.home.articles.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import java.lang.Exception

class ArticlesViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "ArticlesViewModel"
    }

    private var repository: ArticlesRepository =
        ArticlesRepository(
            application
        )

    val categories: MutableLiveData<List<SmallCategoriesModel>> = MutableLiveData()
    val articles: MutableLiveData<List<ArticlesModel>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun getCategories(){
        viewModelScope.launch {
            try {
                categories.postValue(repository.getMainCategories()?.smallCategories)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    fun getArticles(categoryId: Int){
        viewModelScope.launch {
            try {
                articles.postValue(repository.getArticles(categoryId))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}