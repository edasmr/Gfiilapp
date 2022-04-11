package com.genius.gfiilapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class gunlukKazanc(



    @SerializedName("gl_u_kazanc")
    @Expose
    var gl_u_kazanc: String,
    @SerializedName("u_kadi")
    @Expose
    var u_kadi:String,


)
