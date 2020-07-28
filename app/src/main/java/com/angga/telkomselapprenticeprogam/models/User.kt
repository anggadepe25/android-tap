package com.angga.telkomselapprenticeprogam.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("foto") var foto : String? = null,
    @SerializedName("nohp") var nohp : String? = null,
    @SerializedName("api_token") var token : String? = null,
    @SerializedName("branch") var branch : String? = null,
    @SerializedName("jenis_kelamin") var jenis_kelamin : String? = null,
    @SerializedName("tgl_lahir") var tgl_lahir : String? = null,
    @SerializedName("tempat_tinggal") var tempat_tinggal : String? = null,
    @SerializedName("kampus") var kampus : String? = null,
    @SerializedName("jurusan") var jurusan : String? = null,
    @SerializedName("semester") var semester : String? = null,
    @SerializedName("hobi") var hobi : String? = null,
    @SerializedName("instagram") var instagram : String? = null,
    @SerializedName("status") var status : String? = null,
    @SerializedName("point") var point : Int?= null
): Parcelable