package com.tejas.herokudynomanager.network.api


import com.tejas.herokudynomanager.network.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface HerokuApiService {

    @GET("/apps")
    suspend fun getApps(@Header("Authorization") authToken: String): Response<List<HerokuApp>>

    @GET("/apps/{appName}/formation")
    suspend fun getDynoFormation(
        @Header("Authorization") authToken: String,
        @Path("appName") appname: String
    ): Response<List<DynoFormation>>

    @GET("/apps/{appName}/dynos")
    suspend fun getDynos(
        @Header("Authorization") authToken: String,
        @Path("appName") appname: String
    ): Response<List<Dyno>>

    @PATCH("/apps/{appName}/formation")
    suspend fun updateDynoFormation(
        @Header("Authorization") authToken: String,
        @Path("appName") appName: String,
        @Body updatePayload: DynoFormationUpdatePayload
    ): Response<List<DynoFormation>>

    @POST("/apps/{appName}/log-sessions")
    suspend fun getLogSession(
        @Header("Authorization") authToken: String,
        @Path("appName") appName: String,
        @Body payload: LogSessionPayload
    ): Response<LogSessionPayload>

    @GET
    @Streaming
    suspend fun getLog(@Url url: String): Response<ResponseBody>
}