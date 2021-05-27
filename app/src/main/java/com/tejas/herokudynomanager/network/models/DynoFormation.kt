package com.tejas.herokudynomanager.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DynoFormation (
    val app: App?,
    val command: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    val id: String?,
    val type: String?,
    val quantity: Long?,
    val size: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
):Serializable

data class App (
    val id: String?,
    val name: String?
):Serializable
