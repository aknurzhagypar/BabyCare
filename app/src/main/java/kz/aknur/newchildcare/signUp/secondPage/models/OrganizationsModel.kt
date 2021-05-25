package kz.aknur.newchildcare.signUp.secondPage.models

import com.google.gson.annotations.SerializedName

data class OrganizationsModel (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("isActive")
        val isActive: Boolean
)