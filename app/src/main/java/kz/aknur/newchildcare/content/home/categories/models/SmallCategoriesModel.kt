package kz.aknur.newchildcare.content.home.categories.models

import com.google.gson.annotations.SerializedName

data class SmallCategoriesModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("iconUrl")
    val icon: String
)