package kz.aknur.newchildcare.content.home.articles.list

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("data")
    val data: List<ArticlesModel>,
    @SerializedName("status")
    val status: String
)