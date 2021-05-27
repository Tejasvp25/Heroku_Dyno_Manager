package com.tejas.herokudynomanager.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(val id: String? = "",
                 val email: String? = ""):Serializable


data class BuildStack(val name: String? = "",
                      val id: String = ""):Serializable


data class Region(val name: String? = "",
                  val id: String? = ""):Serializable


data class HerokuApp(val owner: Owner?,
                     val internalRouting: String? = "",
                     val stack: Stack?,
                     val archivedAt: String? = "",
                     @SerializedName("created_at")
                     val createdAt: String? = "",
                     val acm: Boolean? = false,
                     val repoSize: Int? = 0,
                     val team: String? = "",
                     @SerializedName("released_at")
                     val releasedAt: String? = "",
                     @SerializedName("buildpack_provided_description")
                     val buildpackProvidedDescription: String? = "",
                     val space: String? = "",
                     @SerializedName("build_stack")
                     val buildStack: BuildStack?,
                     @SerializedName("updated_at")
                     val updatedAt: String? = "",
                     @SerializedName("web_url")
                     val webUrl: String? = "",
                     val organization: String? = "",
                     val name: String? = "",
                     @SerializedName("id")
                     val id: String? = "",
                     @SerializedName("git_url")
                     val gitUrl: String? = "",
                     val region: Region?,
                     val slugSize: Int? = 0,
                     @SerializedName("maintenance")
                     val maintenance: Boolean? = false):Serializable{
    override fun toString(): String {
        return "$name $owner $createdAt ${buildStack?.name} $id"
    }
}


data class Stack(val name: String? = "",
                 val id: String? = ""):Serializable


