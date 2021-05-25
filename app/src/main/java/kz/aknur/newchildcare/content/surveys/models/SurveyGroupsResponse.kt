package kz.aknur.newchildcare.content.surveys.models

import com.google.gson.annotations.SerializedName

data class SurveyGroupsResponse(
    @SerializedName("data")
    val data: List<SurveyGroupsModel>,
    @SerializedName("status")
    val status: String
)
