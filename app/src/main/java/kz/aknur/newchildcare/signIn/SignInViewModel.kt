package kz.aknur.newchildcare.signIn

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kz.aknur.newchildcare.signIn.models.AuthRequest
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private var loginRepository: SignInRepository =
        SignInRepository(
            application
        )

    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()

    fun signIn(authRequest: AuthRequest) {
        viewModelScope.launch {
            try {
                val result = loginRepository.logIn(authRequest)

                isSuccess.postValue(result)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}