package kz.aknur.newchildcare.signUp.secondPage.models

import com.google.gson.annotations.SerializedName

data class GetCitiesResponse(
    @SerializedName("data")
    val data: List<CityModel>,
    @SerializedName("status")
    val status: String
)