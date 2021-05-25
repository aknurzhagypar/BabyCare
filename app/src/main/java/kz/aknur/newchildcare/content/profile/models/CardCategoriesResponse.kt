package kz.aknur.newchildcare.content.profile.models

import com.google.gson.annotations.SerializedName
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel


data class CardCategoriesResponse (
    @SerializedName("data")
    val data: List<SmallCategoriesModel>,
    @SerializedName("status")
    val status: String
)