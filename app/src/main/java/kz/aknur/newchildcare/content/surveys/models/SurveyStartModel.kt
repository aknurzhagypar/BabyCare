package kz.aknur.newchildcare.content.surveys.models

import com.google.gson.annotations.SerializedName

data class SurveyStartModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("questions")
    val questions: List<SurveyQuestionsModel>,
    @SerializedName("riskLevel")
    val riskLevel: Int?
)