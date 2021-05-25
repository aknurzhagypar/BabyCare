package kz.aknur.newchildcare.content.surveys.models

import com.google.gson.annotations.SerializedName

data class SurveyStartResponse(
    @SerializedName("data")
    val data: SurveyStartModel,
    @SerializedName("status")
    val status: String
)