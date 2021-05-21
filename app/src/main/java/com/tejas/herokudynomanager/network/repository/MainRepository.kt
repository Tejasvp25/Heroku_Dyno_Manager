package com.tejas.herokudynomanager.network.repository

import android.util.Log
import com.tejas.herokudynomanager.network.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    var authToken:String = ""

    fun setAuthorizationToken(token:String){
        authToken = "Bearer $token"
    }
    suspend fun getApps() = apiHelper.getApps(authToken)
}