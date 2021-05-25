package kz.aknur.newchildcare.content.profile.models

import com.google.gson.annotations.SerializedName

data class ProfileInfoModel(
    @SerializedName("text")
    val text: String
)