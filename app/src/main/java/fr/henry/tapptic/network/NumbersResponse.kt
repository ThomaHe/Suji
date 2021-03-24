package fr.henry.tapptic.network

import com.google.gson.annotations.SerializedName

class NumbersResponse {
    @SerializedName("name")
    var name:String? = null
    @SerializedName("image")
    var image:String? = null
    @SerializedName("text")
    var text:String? = null

}