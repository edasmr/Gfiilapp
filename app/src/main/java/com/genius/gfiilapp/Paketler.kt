package com.genius.gfiilapp

import com.google.gson.annotations.SerializedName

data class Paketler(

    @SerializedName("id") val id :Int,
    @SerializedName("ad")val ad : String,
    @SerializedName("fiyat") val fiyat: String,
    @SerializedName("resim")val resim : String,
    @SerializedName("l_1")val l_1 : String,
    @SerializedName("l_2")val l_2 : String,
    @SerializedName("l_3")val l_3 : String,
    @SerializedName("l_4")val l_4 : String

)
