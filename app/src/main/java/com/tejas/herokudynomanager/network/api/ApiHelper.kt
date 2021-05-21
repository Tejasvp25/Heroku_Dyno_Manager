package com.tejas.herokudynomanager.network.api

import com.tejas.herokudynomanager.network.models.HerokuApp
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Headers

interface ApiHelper {
    suspend fun getApps(authToken:String): List<HerokuApp>
}