package com.github.pavelkv96.timetable.grsu.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//TODO fix names
@Parcelize
data class Error(
    @SerializedName("code") val code: Int,
    @SerializedName("desc") val message: String?,
    @SerializedName("details") val details: String?
) : Parcelable