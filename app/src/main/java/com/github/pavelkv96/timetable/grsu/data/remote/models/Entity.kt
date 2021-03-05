package com.github.pavelkv96.timetable.grsu.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

abstract class Entity(
    @SerializedName("id") val id: Int = 0
) : Parcelable