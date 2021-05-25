package kz.aknur.newchildcare.content.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.categories.models.LargeCategoriesModel
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import java.lang.Exception

class HomeViewModel(application: Application): AndroidViewModel(application) {

    companion object HomeViewModel{
        const val TAG = "HomeViewModel"
    }

    private var homeRepository: HomeRepository =
        HomeRepository(
            application
        )

    val userCity: MutableLiveData<String> = MutableLiveData()
    val userName: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val largeCategories: MutableLiveData<List<LargeCategoriesModel>> = MutableLiveData()
    val smallCategories: MutableLiveData<List<SmallCategoriesModel>> = MutableLiveData()

    fun getMainCategories(){
        viewModelScope.launch {
            try {
                val result = homeRepository.getMainCategories()
                largeCategories.postValue(result!!.largeCategories)
                smallCategories.postValue(result!!.smallCategories)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    fun getCity() {
        viewModelScope.launch {
            try {
                val result = homeRepository.getCity()
                userCity.postValue(result)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    fun getName() {
        viewModelScope.launch {
            try {
                val result = homeRepository.getName()
                userName.postValue(result)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}