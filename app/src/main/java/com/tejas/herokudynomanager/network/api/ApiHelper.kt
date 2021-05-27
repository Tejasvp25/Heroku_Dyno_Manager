package com.tejas.herokudynomanager.network.api

import com.tejas.herokudynomanager.network.models.Dyno
import com.tejas.herokudynomanager.network.models.DynoFormation
import com.tejas.herokudynomanager.network.models.DynoFormationUpdatePayload
import com.tejas.herokudynomanager.network.models.HerokuApp
import retrofit2.Response


interface ApiHelper {
    suspend fun getApps(authToken:String): Response<List<HerokuApp>>
    suspend fun getDynoFormation(authToken:String, appName:String): Response<List<DynoFormation>>
    suspend fun getDynos(authToken: String,appName: String):Response<List<Dyno>>
    suspend fun updateDynoFormation(authToken: String,appName: String,updatePayload: DynoFormationUpdatePayload):Response<List<DynoFormation>>
}