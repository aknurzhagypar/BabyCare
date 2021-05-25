package kz.aknur.newchildcare.content.home.articles.list

import com.google.gson.annotations.SerializedName

data class AddNewFavArticleRequest(
    @SerializedName("articleId")
    val articleId: Int
)