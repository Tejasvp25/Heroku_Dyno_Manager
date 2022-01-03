package com.tejas.herokudynomanager.network.api

import com.tejas.herokudynomanager.network.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Part
import retrofit2.http.Path


interface ApiHelper {
    suspend fun getApps(authToken: String): Response<List<HerokuApp>>
    suspend fun getDynoFormation(authToken: String, appName: String): Response<List<DynoFormation>>
    suspend fun getDynos(authToken: String, appName: String): Response<List<Dyno>>
    suspend fun updateDynoFormation(
        authToken: String,
        appName: String,
        updatePayload: DynoFormationUpdatePayload
    ): Response<List<DynoFormation>>

    suspend fun getLogSession(
        authToken: String,
        appName: String,
        payload: LogSessionPayload
    ): Response<LogSessionPayload>
    suspend fun getLog(url: String): Response<ResponseBody>
}