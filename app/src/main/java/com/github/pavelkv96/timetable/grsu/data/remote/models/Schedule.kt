package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Schedule(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("days")
    val days: List<Day>? = null
) : BaseEntity()