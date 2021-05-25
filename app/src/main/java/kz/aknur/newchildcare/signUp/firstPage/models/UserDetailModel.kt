package kz.aknur.newchildcare.signUp.firstPage.models

import com.google.gson.annotations.SerializedName

data class UserDetailModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("cityId")
    val cityId: Int?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("polyclinicId")
    val polyclinicId: Int?,
    @SerializedName("isPregnant")
    val isPregnant: Boolean?,
    @SerializedName("pregnancyWeekCount")
    val pregnancyWeekCount: Int?
)