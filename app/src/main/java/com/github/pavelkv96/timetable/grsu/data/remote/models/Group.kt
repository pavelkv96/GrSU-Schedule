package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Group(
    @SerializedName("title") val title: String,
    @SerializedName("course") val course: Int,
    @SerializedName("students") val students: Int,
    @SerializedName("department") val department: Department?,
    @SerializedName("faculty") val faculty: Faculty?
) : Entity()