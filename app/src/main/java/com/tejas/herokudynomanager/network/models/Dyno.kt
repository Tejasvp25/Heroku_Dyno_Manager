package com.tejas.herokudynomanager.network.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Dyno(@SerializedName("app")
                val app: App?,
                @SerializedName("size")
                val size: String? ,
                @SerializedName("updated_at")
                val updatedAt: String? ,
                @SerializedName("release")
                val release: Release?,
                @SerializedName("name")
                val name: String? ,
                @SerializedName("created_at")
                val createdAt: String? ,
                @SerializedName("id")
                val id: String? ,
                @SerializedName("state")
                val state: String? ,
                @SerializedName("type")
                val type: String? ,
                @SerializedName("command")
                val command: String? ):Serializable


data class Release(@SerializedName("id")
                   val id: String? ,
                   @SerializedName("version")
                   val version: Int? ):Serializable


