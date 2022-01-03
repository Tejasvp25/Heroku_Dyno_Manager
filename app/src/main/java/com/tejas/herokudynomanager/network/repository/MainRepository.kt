package com.tejas.herokudynomanager.network.repository

import com.tejas.herokudynomanager.network.api.ApiHelper
import com.tejas.herokudynomanager.network.models.DynoFormation
import com.tejas.herokudynomanager.network.models.DynoFormationUpdatePayload
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    var authToken:String = ""

    fun setAuthorizationToken(token:String){
        authToken = "Bearer $token"
    }
    suspend fun getApps() = apiHelper.getApps(authToken)
    suspend fun getDynoFormation(appName:String) = apiHelper.getDynoFormation(authToken, appName)
    suspend fun getDynos(appName: String) = apiHelper.getDynos(authToken,appName)
    suspend fun updateDynoFormation(appName: String
                                             ,updatePayload: DynoFormationUpdatePayload
    ): Response<List<DynoFormation>>
            = apiHelper.updateDynoFormation(authToken,appName,updatePayload)
    suspend fun getLog(url: String) = apiHelper.getLog(url)
}