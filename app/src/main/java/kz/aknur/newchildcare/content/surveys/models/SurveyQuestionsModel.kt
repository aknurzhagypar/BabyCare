package kz.aknur.newchildcare.content.surveys.models

import com.google.gson.annotations.SerializedName

data class SurveyQuestionsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
)