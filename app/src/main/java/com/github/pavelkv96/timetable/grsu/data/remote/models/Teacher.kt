package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
data class Teacher(
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("patronym") val patronymic: String?,
    @SerializedName("fullname") val fullName: String?,
    @SerializedName("post") val post: String?
) : Entity()
