package com.github.pavelkv96.timetable.grsu.data.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Entities<E : Entity>(
    @SerializedName("items") val items: List<E>?
) : BaseEntity()