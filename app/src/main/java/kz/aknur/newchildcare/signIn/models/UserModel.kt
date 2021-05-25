package kz.aknur.newchildcare.signIn.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String
)