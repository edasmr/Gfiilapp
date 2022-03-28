package com.genius.gfiilapp

import com.google.gson.annotations.SerializedName

data class Gorevler(

    @SerializedName("id") val id :Int,
    @SerializedName("img") val img :String,
    @SerializedName("text1")val text1 : String,
    @SerializedName("text2") val text2: String,
    @SerializedName("url")val url : String

)
