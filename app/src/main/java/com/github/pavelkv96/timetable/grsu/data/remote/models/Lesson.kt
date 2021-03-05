package com.github.pavelkv96.timetable.grsu.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Lesson(
    @SerializedName("timeStart") val timeStart: String?,
    @SerializedName("timeEnd") val timeEnd: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("room") val room: String?,
    @SerializedName("groups") val groups: List<Group>?,
    @SerializedName("subgroup") val subgroup: Subgroup?,
    @SerializedName("teacher") val teacher: Teacher?
) : Parcelable