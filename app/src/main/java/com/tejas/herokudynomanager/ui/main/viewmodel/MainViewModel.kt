package com.tejas.herokudynomanager.ui.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import com.tejas.herokudynomanager.network.repository.MainRepository
import com.tejas.herokudynomanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(handle: SavedStateHandle,private val mainRepository:MainRepository):ViewModel() {

    fun setAuthorizationToken(token:String) = mainRepository.setAuthorizationToken(token)

    fun getApps() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data=null))
        try {
            emit(Resource.success(data=mainRepository.getApps()))
        }catch (exception:Exception){
            emit(Resource.error(data=null,message = exception.localizedMessage ?: "Error Occured"))
        }
    }
}