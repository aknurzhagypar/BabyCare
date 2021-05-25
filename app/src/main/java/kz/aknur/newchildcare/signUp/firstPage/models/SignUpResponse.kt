package kz.aknur.newchildcare.signUp.firstPage.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("error_description")
    val error_description: String,
    @SerializedName("data")
    val data: UserModel
)