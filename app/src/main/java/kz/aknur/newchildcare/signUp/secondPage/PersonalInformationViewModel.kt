package kz.aknur.newchildcare.signUp.secondPage

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.common.helpers.UtilsObject
import kz.aknur.newchildcare.signUp.secondPage.models.CityModel
import kz.aknur.newchildcare.signUp.secondPage.models.OrganizationsModel
import kz.aknur.newchildcare.signUp.secondPage.models.PersonalInformationRequest
import retrofit2.Response
import java.lang.Exception

class PersonalInformationViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "PersonalInformationViewModel"
    }

    private var repository: PersonalInformationRepository =
            PersonalInformationRepository(
                    application
            )

    val listOfCity: MutableLiveData<List<CityModel>> = MutableLiveData()
    val listOfOrg: MutableLiveData<List<OrganizationsModel>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()
    val leaveReg: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("LongLogTag")
    fun getCities() {
        viewModelScope.launch {
            try {
                val result = repository.getCities()
                Log.e(TAG, result.toString())
                if (result!=null) {
                    listOfCity.postValue(result)
                } else {
                    isError.postValue(null)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun getOrganizations() {
        viewModelScope.launch {
            try {
                val result = repository.getOrganizations()
                Log.e(TAG, result.toString())
                if (result!=null) {
                    listOfOrg.postValue(result)
                } else {
                    isError.postValue(null)
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun sendUserInfo(user: PersonalInformationRequest) {
        viewModelScope.launch {
            try {
                leaveReg.postValue(repository.sendUserInfo(user))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }

    private fun <T> handleErrorBody(response: Response<T>) {
        isError.postValue(UtilsObject.handleErrorMessage(response))
    }



}