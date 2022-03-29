package com.genius.gfiilapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("u_kadi")
    @Expose
    var u_kadi:String,
    @SerializedName("u_mail")
    @Expose
    var u_mail: String,
    @SerializedName("u_telefon_no")
    @Expose
    var u_telefon_no: String,
    @SerializedName("u_cinsiyet")
    @Expose
    var u_cinsiyet: String,
    @SerializedName("u_bakiye")
    @Expose
    var u_bakiye: String,

    @SerializedName("ana_bakiye")
    @Expose
    var ana_bakiye: String
)
