package kz.aknur.newchildcare.content.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import java.lang.Exception

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    companion object HomeViewModel{
        const val TAG = "ProfileViewModel"
    }

    private var repository: ProfileRepository =
        ProfileRepository(
            application
        )

    val isLogOut: MutableLiveData<Boolean> = MutableLiveData()
    val profileText: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val cardCategories: MutableLiveData<List<SmallCategoriesModel>> = MutableLiveData()

    fun getCardCategories(){
        viewModelScope.launch {
            try {
                val result = repository.getCardCategories()
                cardCategories.postValue(result!!)
            } catch (exception: Exception) {
                Log.e(kz.aknur.newchildcare.content.home.HomeViewModel.TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            try {
                isLogOut.postValue(repository.logOut())
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    fun getProfileInfo(){
        viewModelScope.launch {
            try {
                profileText.postValue(repository.getProfileInfo()!!.text)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}