package com.tejas.herokudynomanager.network.api

import com.tejas.herokudynomanager.network.models.HerokuApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface HerokuApiService {

    @GET("/apps")
    suspend fun getApps(@Header("Authorization") authToken:String): List<HerokuApp>

}