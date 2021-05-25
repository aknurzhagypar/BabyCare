package kz.aknur.newchildcare.content.home.articles.details

import com.google.gson.annotations.SerializedName
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel

data class ArticleResponse(
    @SerializedName("data")
    val data: ArticlesModel,
    @SerializedName("status")
    val status: String
)