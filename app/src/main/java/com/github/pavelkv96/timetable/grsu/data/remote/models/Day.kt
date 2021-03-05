package com.github.pavelkv96.timetable.grsu.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Day(
    @SerializedName("num") val num: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("date") val date: String?,
    @SerializedName("lessons") val lessons: List<Lesson>?
) : Parcelable