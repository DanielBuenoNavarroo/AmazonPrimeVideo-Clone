package com.example.mvvmjetpackcomposelogin.data.api.model

import com.google.gson.annotations.SerializedName

data class PersonModel(
    @SerializedName("id") val id: Int,
    @SerializedName("profile_path") val profilePath: String?
)
