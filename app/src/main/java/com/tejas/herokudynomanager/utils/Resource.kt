package com.tejas.herokudynomanager.utils

data class Resource<out T>(val status: Status,val data:T?,val message:String?,val statusCode: Int?) {
    companion object{
        fun <T> success(data:T):Resource<T> = Resource(status = Status.SUCCESS,data = data,message = null,statusCode = null)
        fun <T> error(data:T?,message: String?,statusCode: Int?): Resource<T> = Resource(status = Status.ERROR,data=data,message = message,statusCode = null)
        fun <T> loading(data:T):Resource<T> = Resource(status = Status.SUCCESS,data = data,message = null,statusCode = null)
    }
}