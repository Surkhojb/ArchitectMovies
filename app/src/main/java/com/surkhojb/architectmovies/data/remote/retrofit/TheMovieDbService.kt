package com.surkhojb.architectmovies.data.remote.retrofit

import com.surkhojb.architectmovies.data.remote.model.MovieCast
import com.surkhojb.architectmovies.data.remote.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("movie/top_rated")
    suspend fun getTopRated(
      @Query("region") region: String,
        @Query("page") page: Int? = null
    ): MovieResult

    @GET("movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int
    ): MovieCast
}