package com.angga.telkomselapprenticeprogam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Program(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("judul") var judul: String? = null,
    @SerializedName("panduan") var panduan: String? = null,
    @SerializedName("gambar") var gambar: String? = null,
    @SerializedName("tanggal_mulai_pengumpulan") var tanggal_mulai_pengumpulan: String? = null,
    @SerializedName("tanggal_selesai_pengumpulan") var tanggal_selesai_pengumpulan: String? = null
) : Parcelable