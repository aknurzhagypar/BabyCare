package kz.aknur.newchildcare.signIn.models

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)