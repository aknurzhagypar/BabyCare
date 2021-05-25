package kz.aknur.newchildcare.content.surveys.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.surveys.models.SurveyStartModel

class SurveyStartViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "SurveyStartViewModel"
    }

    private var repository: SurveyStartRepository =
        SurveyStartRepository(
            application
        )

    val surveyQuestions: MutableLiveData<SurveyStartModel> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun startSurvey(month: Int, groupId: Int){
        viewModelScope.launch {
            try {
                surveyQuestions.postValue(repository.startSurvey(month, groupId))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}