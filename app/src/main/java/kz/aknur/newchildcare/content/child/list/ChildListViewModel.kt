package kz.aknur.newchildcare.content.child.list

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChildListViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        const val TAG = "ChildListViewModel"
    }

    private var repository: ChildListRepository =
        ChildListRepository(
                    application
            )


    val isError: MutableLiveData<String> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val childList: MutableLiveData<List<ChildListResponse.ChildModel>> = MutableLiveData()



   @SuppressLint("LongLogTag")
    fun getMyChild() {
        viewModelScope.launch {
            val result = repository.getMyChild()
            when(result.isSuccessful){
                true -> {
                    childList.postValue(result.body()?.data)
                }
                false -> {

                }

            }
        }
    }




}