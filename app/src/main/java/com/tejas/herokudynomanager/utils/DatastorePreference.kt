package com.tejas.herokudynomanager.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastorePreference @Inject constructor(@ApplicationContext private val context: Context){

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "api_pref")
    companion object{
        val API_KEY = stringPreferencesKey("api_key")
    }

    suspend fun saveApikey(apiKey:String){
        context.dataStore.edit { it[API_KEY] = apiKey }
    }

    val apiKey: Flow<String> = context.dataStore.data.map {
        it[API_KEY] ?: ""
    }

}