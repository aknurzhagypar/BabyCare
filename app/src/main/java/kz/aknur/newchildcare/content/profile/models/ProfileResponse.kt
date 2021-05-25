package kz.aknur.newchildcare.content.profile.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    val data: ProfileInfoModel,
    @SerializedName("status")
    val status: String
)