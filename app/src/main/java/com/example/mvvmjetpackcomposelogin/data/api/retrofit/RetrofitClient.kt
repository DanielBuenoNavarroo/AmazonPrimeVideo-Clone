package com.example.mvvmjetpackcomposelogin.data.api.retrofit

import com.example.mvvmjetpackcomposelogin.data.api.apiinterface.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val movieService: MovieService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

}