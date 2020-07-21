package com.angga.telkomselapprenticeprogam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reward(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("judul") var judul: String? = null,
    @SerializedName("keterangan") var keterangan: String? = null
) : Parcelable