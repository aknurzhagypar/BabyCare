package kz.aknur.newchildcare.content.surveys.groups

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.surveys.models.SurveyGroupsModel

class SurveyGroupsViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "SurveyGroupsViewModel"
    }

    private var repository: SurveyGroupsRepository =
        SurveyGroupsRepository(
            application
        )

    val surveyGroups: MutableLiveData<List<SurveyGroupsModel>> = MutableLiveData()
    val profileText: MutableLiveData<String> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun getSurveyGroups(){
        viewModelScope.launch {
            try {
                surveyGroups.postValue(repository.getSurveyGroups())
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}