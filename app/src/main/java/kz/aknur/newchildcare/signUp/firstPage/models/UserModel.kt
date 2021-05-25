package kz.aknur.newchildcare.signUp.firstPage.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserDetailModel
)