package kz.aknur.newchildcare.content.child.add.models

import com.google.gson.annotations.SerializedName

data class ChildAddRequest(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("disease")
    val disease: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("weight")
    val weight: Int
)