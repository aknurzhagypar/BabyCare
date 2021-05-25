package kz.aknur.newchildcare.content.hospitals.models

import com.google.gson.annotations.SerializedName

data class HospitalsResponse(
    @SerializedName("data")
    val data: List<HospitalModel>,
    @SerializedName("status")
    val status: String
)