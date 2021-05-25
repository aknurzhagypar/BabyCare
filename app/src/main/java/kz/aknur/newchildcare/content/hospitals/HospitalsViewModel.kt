package kz.aknur.newchildcare.content.hospitals

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.hospitals.models.HospitalModel
import java.lang.Exception

class HospitalsViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "HospitalsViewModel"
    }

    private var repository: HospitalsRepository =
        HospitalsRepository(
            application
        )

    val hospitals: MutableLiveData<List<HospitalModel>> = MutableLiveData()
    val profileText: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun getHospitals(){
        viewModelScope.launch {
            try {
                hospitals.postValue(repository.getHospitals())
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}