package com.angga.telkomselapprenticeprogam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Info(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("judul") var judul: String? = null,
    @SerializedName("gambar") var gambar: String? = null,
    @SerializedName("deskripsi") var deskripsi: String? = null
) : Parcelable