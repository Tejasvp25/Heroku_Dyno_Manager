package com.tejas.herokudynomanager.network.models

data class Owner(val id: String? = "",
                 val email: String? = "")


data class BuildStack(val name: String? = "",
                      val id: String = "")


data class Region(val name: String? = "",
                  val id: String? = "")


data class HerokuApp(val owner: Owner?,
                     val internalRouting: String? = "",
                     val stack: Stack?,
                     val archivedAt: String? = "",
                     val createdAt: String? = "",
                     val acm: Boolean? = false,
                     val repoSize: Int? = 0,
                     val team: String? = "",
                     val releasedAt: String? = "",
                     val buildpackProvidedDescription: String? = "",
                     val space: String? = "",
                     val buildStack: BuildStack?,
                     val updatedAt: String? = "",
                     val webUrl: String? = "",
                     val organization: String? = "",
                     val name: String? = "",
                     val id: String? = "",
                     val gitUrl: String? = "",
                     val region: Region?,
                     val slugSize: Int? = 0,
                     val maintenance: Boolean? = false){
    override fun toString(): String {
        return name!!
    }
}


data class Stack(val name: String? = "",
                 val id: String? = "")


