package com.angga.telkomselapprenticeprogam.models

import com.google.gson.annotations.SerializedName

data class Challenge(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("link") var link : String? = null,
    @SerializedName("id_program") var id_program : Int? = null,
    @SerializedName("user") var user : User
)