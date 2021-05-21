package com.tejas.herokudynomanager.di.module

import com.tejas.herokudynomanager.BuildConfig
import com.tejas.herokudynomanager.network.api.ApiHelper
import com.tejas.herokudynomanager.network.api.ApiHelperImpl
import com.tejas.herokudynomanager.network.api.HerokuApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBaseUrl() = "https://api.heroku.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient() = if(BuildConfig.DEBUG){
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient
            .Builder()
            .addInterceptor(logginInterceptor)
            .addInterceptor(Interceptor {
                chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Accept","application/vnd.heroku+json; version=3")
                return@Interceptor chain.proceed(builder.build())
            })
            .build()
    }else{
        OkHttpClient
            .Builder()
            .addInterceptor(Interceptor {
                    chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Accept","application/vnd.heroku+json; version=3")
                return@Interceptor chain.proceed(builder.build())
            })
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(HerokuApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,BASE_URL:String):Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

}