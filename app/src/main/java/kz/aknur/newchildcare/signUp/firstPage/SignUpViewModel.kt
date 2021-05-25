package kz.aknur.newchildcare.signUp.firstPage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kz.aknur.newchildcare.signUp.firstPage.models.SignUpRequest
import kotlinx.coroutines.launch
import java.lang.Exception

class SignUpViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "SignUpViewModel"
    }

    private var signUpRepository: SignUpRepository =
        SignUpRepository(
            application
        )

    val isSuccess: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()

    fun signUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            try {
                val result = signUpRepository.signUp(signUpRequest)
                Log.d(TAG, result.toString())
                if (result!="null" && result.length<5){
                    isSuccess.postValue(result)
                } else {
                    isError.postValue(result)
                }

            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}