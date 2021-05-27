package com.tejas.herokudynomanager.network.api


import com.tejas.herokudynomanager.network.models.Dyno
import com.tejas.herokudynomanager.network.models.DynoFormation
import com.tejas.herokudynomanager.network.models.DynoFormationUpdatePayload
import com.tejas.herokudynomanager.network.models.HerokuApp
import retrofit2.Response
import retrofit2.http.*

interface HerokuApiService {

    @GET("/apps")
    suspend fun getApps(@Header("Authorization") authToken:String): Response<List<HerokuApp>>

    @GET("/apps/{appName}/formation")
    suspend fun getDynoFormation(@Header("Authorization") authToken:String,@Path("appName")appname:String): Response<List<DynoFormation>>

    @GET("/apps/{appName}/dynos")
    suspend fun getDynos(@Header("Authorization") authToken:String,@Path("appName")appname:String): Response<List<Dyno>>

    @PATCH("/apps/{appName}/formation")
    suspend fun updateDynoFormation(@Header("Authorization") authToken: String
                                    ,@Path("appName")appName: String
                                    ,@Body updatePayload: DynoFormationUpdatePayload):Response<List<DynoFormation>>

}