package com.surkhojb.architectmovies.data.remote

import com.surkhojb.architectmovies.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieResult
}