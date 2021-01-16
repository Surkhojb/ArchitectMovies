package com.surkhojb.architectmovies.data.remote.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDb {
    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val okHttpClient = HttpLoggingInterceptor().run{
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(this)
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    val service : TheMovieDbService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create(TheMovieDbService::class.java)
        }
}

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key","cc0fba5e2e944d686f8aa373cbcf9e83")
            .build()
        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

}