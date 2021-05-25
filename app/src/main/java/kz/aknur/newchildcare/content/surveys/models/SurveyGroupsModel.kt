package kz.aknur.newchildcare.content.surveys.models

import com.google.gson.annotations.SerializedName

data class SurveyGroupsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("isActive")
    val isActive: Boolean
)