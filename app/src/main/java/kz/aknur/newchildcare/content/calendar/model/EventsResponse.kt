package kz.aknur.newchildcare.content.calendar.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("data")
    val data: List<EventsModel>
){
    data class EventsModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("date")
        val date: String?,
        @SerializedName("time")
        val time: String?,
        @SerializedName("remindAtDate")
        val remindAtDate: String,
        @SerializedName("remindAtTime")
        val remindAtTime: String,
        @SerializedName("notes")
        val notes: String
    )
}