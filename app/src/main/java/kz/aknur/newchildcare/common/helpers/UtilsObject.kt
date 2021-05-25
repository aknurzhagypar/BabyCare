package kz.aknur.newchildcare.common.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response


object UtilsObject {

    fun <T> handleErrorMessage(response: Response<T>): String {
        val type = object : TypeToken<ExceptionModel>() {}.type
        val errorBody: ExceptionModel = Gson().fromJson(response.errorBody()!!.charStream(), type)
        return errorBody.message()
    }


}