package com.tejas.herokudynomanager.ui.main.viewmodel

import androidx.lifecycle.*
import com.tejas.herokudynomanager.network.models.HerokuApp

import com.tejas.herokudynomanager.network.repository.MainRepository
import com.tejas.herokudynomanager.utils.DatastorePreference
import com.tejas.herokudynomanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AppListActivityViewModel @Inject constructor(handle: SavedStateHandle, private val mainRepository:MainRepository):ViewModel() {

    private val _apps = MutableLiveData<Resource<List<HerokuApp>?>>()
    val apps: MutableLiveData<Resource<List<HerokuApp>?>>
        get() = _apps

    fun setAuthorizationToken(token:String) = mainRepository.setAuthorizationToken(token)
    fun getApps() = viewModelScope.launch(Dispatchers.IO) {
        _apps.postValue(Resource.loading(data=null))
        try {
            val res = mainRepository.getApps()
            if(res.code() == 200)
                _apps.postValue(Resource.success(data=res.body()))
            else
                _apps.postValue(Resource.error(data = null,message = res.message() ?: "Error Occured",statusCode = res.code()))
        }catch (exception:Exception){
            _apps.postValue(Resource.error(data=null,message = exception.localizedMessage ?: "Error Occured",statusCode = 500))
        }
    }
}