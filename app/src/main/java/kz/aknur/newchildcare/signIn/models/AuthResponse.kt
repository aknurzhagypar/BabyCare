package kz.aknur.newchildcare.signIn.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val data: DataModel,
    @SerializedName("status")
    val status: String
)