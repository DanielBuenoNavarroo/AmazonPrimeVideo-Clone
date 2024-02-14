package com.example.mvvmjetpackcomposelogin.data.api.apiinterface

import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleMovieModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    //PELICULAS
    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : SingleMovieModel

    // SERIES
    @GET("tv/top_rated")
    suspend fun getSeriesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList
    @GET("tv/popular")
    suspend fun getSeriesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("tv/{tvId}")
    suspend fun getSeriesById(
        @Path("movieId") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : SingleMovieModel

}

data class MovieResponseList(
    @SerializedName("results") val results: List<MoviesModel>
)