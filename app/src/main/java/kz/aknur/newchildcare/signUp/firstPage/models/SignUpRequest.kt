package kz.aknur.newchildcare.signUp.firstPage.models

import com.google.gson.annotations.SerializedName

data class  SignUpRequest(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)