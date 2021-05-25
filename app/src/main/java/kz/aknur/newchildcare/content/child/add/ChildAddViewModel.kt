package kz.aknur.newchildcare.content.child.add

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.child.add.models.ChildAddRequest
import java.lang.Exception

class ChildAddViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "ChildAddViewModel"
    }

    private var repository: ChildAddRepository =
        ChildAddRepository(
                    application
            )


    val isError: MutableLiveData<String> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()



    @SuppressLint("LongLogTag")
    fun addNewChild(childAddRequest: ChildAddRequest) {
        viewModelScope.launch {
            try {
                val result = repository.addChild(childAddRequest)
                Log.e(TAG, result.toString())
                if (result) {
                    isSuccess.postValue(result)
                } else {
                    isError.postValue(null)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }




}