package com.tejas.herokudynomanager.network.models


import com.google.gson.annotations.SerializedName

data class DynoFormationUpdatePayload(@SerializedName("updates")
                                      val updates: List<UpdatesItem>){
    override fun toString(): String {
        return updates.toString()
    }
}


data class UpdatesItem(@SerializedName("quantity")
                       val quantity: Int ,
                       @SerializedName("type")
                       val type: String ){
    override fun toString(): String {
        return "$quantity $type"
    }
}


