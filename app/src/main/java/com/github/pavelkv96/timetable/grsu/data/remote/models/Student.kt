package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Student(
    @SerializedName("fullname") val fullname: String?,
    @SerializedName("grouptitle") val grouptitle: String?,
    @SerializedName("studenttype") val studenttype: String?,
    @SerializedName("nzach") val nzach: String?
) : Entity()