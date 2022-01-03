package com.tejas.herokudynomanager.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.tejas.herokudynomanager.network.models.Dyno
import com.tejas.herokudynomanager.network.models.DynoFormation
import com.tejas.herokudynomanager.network.models.DynoFormationUpdatePayload
import com.tejas.herokudynomanager.network.repository.MainRepository
import com.tejas.herokudynomanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import android.R.attr.process
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection


@HiltViewModel
class AppInfoViewModel @Inject constructor(handle: SavedStateHandle, val mainRepository: MainRepository): ViewModel() {

    companion object{
        val TAG = this.javaClass.simpleName
    }

    private val _dynoFormations = MutableLiveData<Resource<List<DynoFormation>?>>()
    val dynoFormations: MutableLiveData<Resource<List<DynoFormation>?>>
        get() = _dynoFormations

    private val _dynos = MutableLiveData<Resource<List<Dyno>?>>()
    val dynos: MutableLiveData<Resource<List<Dyno>?>>
        get() = _dynos

    private val _log = MutableLiveData<Resource<ResponseBody?>>()
    val log: MutableLiveData<Resource<ResponseBody?>>
        get() = _log


    fun getDynoFormation(appName:String,delay:Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
       _dynoFormations.postValue(Resource.loading(null))
        try {
            if(delay)
                delay(3000L)
            val res = mainRepository.getDynoFormation(appName)
            if(res.code() == 200)
                _dynoFormations.postValue(Resource.success(data = res.body()))
            else
                _dynoFormations.postValue(Resource.error(data = null,message = res.message() ?: "Error Occured",statusCode = res.code()))
        }catch (exception:Exception){
            _dynoFormations.postValue(Resource.error(data = null,message = exception.localizedMessage ?: "Error Occured",statusCode = 500))
        }
    }

    fun getDynos(appName:String,delay:Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        _dynos.postValue(Resource.loading(null))
        try {
            if(delay)
                delay(3000L)
            val res = mainRepository.getDynos(appName)
            if(res.code()==200)
                _dynos.postValue(Resource.success(data = res.body()))
            else
                _dynos.postValue(Resource.error(data = null,message = res.message() ?: "Error Occured",statusCode = res.code()))
        }catch (exception:Exception){
            _dynos.postValue(Resource.error(data = null,message = exception.localizedMessage ?: "Error Occured",statusCode = 500))
        }
    }

    fun updateDynoFormation(appName:String,updatePayload:DynoFormationUpdatePayload) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.updateDynoFormation(appName,updatePayload)
            if(res.code() == 200)
                emit(Resource.success(data = res.body()))
            else
                emit(Resource.error(data = null,message = res.message() ?: "Error Occured",statusCode = res.code()))
        }catch (exception:Exception){
            emit(Resource.error(data = null,message = exception.localizedMessage ?: "Error Occured",statusCode = 500))
        }
    }

    fun getLogs(url: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val res = mainRepository.getLog(url)
            _log.postValue(Resource.success(res.body()))
        }catch (exception:Exception){
            _log.postValue(Resource.error(data = null,message = exception.localizedMessage ?: "Error Occured",statusCode = 500))
            exception.printStackTrace()
        }
    }

}