package kz.aknur.newchildcare.content.child.list

import com.google.gson.annotations.SerializedName

data class ChildListResponse(
    @SerializedName("data")
    val data: List<ChildModel>
){
    data class ChildModel(
        @SerializedName("birthDate")
        val birthDate: String,
        @SerializedName("disease")
        val disease: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("height")
        val height: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("parentId")
        val parentId: Int,
        @SerializedName("weight")
        val weight: Int
    )
}