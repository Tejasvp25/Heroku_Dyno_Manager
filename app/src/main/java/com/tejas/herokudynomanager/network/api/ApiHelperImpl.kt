package com.tejas.herokudynomanager.network.api

import com.tejas.herokudynomanager.network.api.ApiHelper
import com.tejas.herokudynomanager.network.api.HerokuApiService
import com.tejas.herokudynomanager.network.models.HerokuApp
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: HerokuApiService): ApiHelper {
    override suspend fun getApps(authToken:String):List<HerokuApp> = apiService.getApps(authToken)
}