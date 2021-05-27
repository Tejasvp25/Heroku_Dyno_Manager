package com.tejas.herokudynomanager.di.module

import android.content.Context
import android.util.Log
import com.tejas.herokudynomanager.BuildConfig
import com.tejas.herokudynomanager.network.api.ApiHelper
import com.tejas.herokudynomanager.network.api.ApiHelperImpl
import com.tejas.herokudynomanager.network.api.HerokuApiService
import com.tejas.herokudynomanager.network.repository.MainRepository
import com.tejas.herokudynomanager.utils.DatastorePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
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
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context):DatastorePreference = DatastorePreference(context)

    @Provides
    @Singleton
    fun provideRepository(apiHelper: ApiHelperImpl) = MainRepository(apiHelper)
}