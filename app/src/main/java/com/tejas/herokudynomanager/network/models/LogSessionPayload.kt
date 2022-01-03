package com.tejas.herokudynomanager.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogSessionPayload(
    @SerializedName("dyno") val dyno: String,
    @SerializedName("lines") val lines: Int,
    @SerializedName("source") val source: String,
    @SerializedName("tail") val tail: Boolean
) : Serializable {
    companion object {
        fun Default(): LogSessionPayload {
            return LogSessionPayload("web.1",10,"app",true)
        }
    }

    override fun toString(): String {
        return "$dyno ${lines.toString()} $source ${tail.toString()}"
    }
}