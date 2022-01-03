package com.tejas.herokudynomanager.network.api


import com.tejas.herokudynomanager.network.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Part
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: HerokuApiService) : ApiHelper {
    override suspend fun getApps(authToken: String): Response<List<HerokuApp>> =
        apiService.getApps(authToken)

    override suspend fun getDynoFormation(
        authToken: String,
        appName: String
    ): Response<List<DynoFormation>> = apiService.getDynoFormation(authToken, appName)

    override suspend fun getDynos(authToken: String, appName: String): Response<List<Dyno>> =
        apiService.getDynos(authToken, appName)

    override suspend fun updateDynoFormation(
        authToken: String, appName: String, updatePayload: DynoFormationUpdatePayload
    ): Response<List<DynoFormation>> =
        apiService.updateDynoFormation(authToken, appName, updatePayload)

    override suspend fun getLogSession(
        authToken: String,
        appName: String,
        payload: LogSessionPayload
    ) = apiService.getLogSession(authToken, appName, payload)

    override suspend fun getLog(url: String) = apiService.getLog(url)
}