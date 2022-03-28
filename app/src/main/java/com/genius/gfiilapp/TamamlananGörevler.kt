package com.genius.gfiilapp

import com.google.gson.annotations.SerializedName

data class TamamlananGörevler(

    @SerializedName("g_id") val g_id :Int,
    @SerializedName("gl_u_id")val ad : String,
    @SerializedName("tarih") val tarih: String,
    @SerializedName("kazanc")val kazanc : String,
    @SerializedName("baslik")val baslik : String,
    @SerializedName("durum")val durum : Int,


)
