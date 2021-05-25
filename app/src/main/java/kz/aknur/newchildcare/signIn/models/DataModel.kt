package kz.aknur.newchildcare.signIn.models

import com.google.gson.annotations.SerializedName
import kz.aknur.newchildcare.signUp.firstPage.models.UserDetailModel

data class DataModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserDetailModel
)