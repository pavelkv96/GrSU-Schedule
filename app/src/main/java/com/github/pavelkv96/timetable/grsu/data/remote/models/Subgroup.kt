package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
class Subgroup(
    @SerializedName("title") val title: String?
) : Entity()