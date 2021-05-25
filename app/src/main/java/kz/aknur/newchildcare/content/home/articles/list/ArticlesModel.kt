package kz.aknur.newchildcare.content.home.articles.list

import com.google.gson.annotations.SerializedName


data class ArticlesModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("topic")
    val topic: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("iconUrl")
    val icon: String?,
    @SerializedName("isFavourite")
    var isFavourite: Boolean
)