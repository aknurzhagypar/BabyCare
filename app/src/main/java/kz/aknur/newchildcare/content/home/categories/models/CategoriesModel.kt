package kz.aknur.newchildcare.content.home.categories.models

import com.google.gson.annotations.SerializedName

data class CategoriesModel (
    @SerializedName("largeCategories")
    val largeCategories: List<LargeCategoriesModel>,
    @SerializedName("smallCategories")
    val smallCategories: List<SmallCategoriesModel>
)