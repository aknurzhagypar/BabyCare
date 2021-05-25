package kz.aknur.newchildcare.content.surveys.survey

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.surveys.models.SurveyStartModel
import java.lang.Exception

class SurveyViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "SurveyViewModel"
    }

    private var repository: SurveyRepository =
        SurveyRepository(
            application
        )

    val surveyQuestions: MutableLiveData<SurveyStartModel> = MutableLiveData()
    val isSended: MutableLiveData<Boolean> = MutableLiveData()
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

    fun sendAnswer(id: Int, answerList: JsonArray){
        viewModelScope.launch {
            try {
                isSended.postValue(repository.sendAnswer(id, answerList))
            } catch (exception: Exception) {
                Log.e(TAG, exception.message.toString())
                isError.postValue(exception.toString())
            }
        }
    }
}