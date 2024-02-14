package com.example.mvvmjetpackcomposelogin.data

sealed class MediaType(val nombre : String) {
    data object Movie : MediaType("movie")
    data object Serie : MediaType("tvShow")
}