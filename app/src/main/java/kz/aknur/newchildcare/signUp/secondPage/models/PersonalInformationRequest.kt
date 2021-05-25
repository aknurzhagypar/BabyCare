package kz.aknur.newchildcare.signUp.secondPage.models

import com.google.gson.annotations.SerializedName

data class  PersonalInformationRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isPregnant")
    val isPregnant: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("polyclinicId")
    val polyclinicId: Int,
    @SerializedName("pregnancyWeekCount")
    val pregnancyWeekCount: Int
)