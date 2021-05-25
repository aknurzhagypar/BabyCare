package kz.aknur.newchildcare.content.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.content.calendar.model.EventsResponse
import java.lang.Exception

class CalendarViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "CalendarViewModel"
    }

    private var repository: CalendarRepository =
        CalendarRepository(
            application
        )


    val events: MutableLiveData<List<EventsResponse.EventsModel>> = MutableLiveData()
    val isError: MutableLiveData<String> = MutableLiveData()


    fun getArticles(){
        viewModelScope.launch {
            try {
                events.postValue(repository.getEvents())
            } catch (exception: Exception) {
                isError.postValue(exception.toString())
            }
        }
    }
}