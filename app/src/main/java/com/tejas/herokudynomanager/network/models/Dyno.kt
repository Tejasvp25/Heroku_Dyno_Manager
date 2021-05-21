package com.tejas.herokudynomanager.network.models

data class App(val name: String? = "",
               val id: String? = "")


data class Dyno(val app: App?,
                val attachUrl: String? = "",
                val size: String? = "",
                val updatedAt: String? = "",
                val release: Release?,
                val name: String? = "",
                val createdAt: String? = "",
                val id: String? = "",
                val state: String? = "",
                val type: String? = "",
                val command: String? = "")


data class Release(val id: String? = "",
                   val version: Int? = 0)


