package com.genius.gfiilapp

import com.google.gson.annotations.SerializedName

data class TamamlananOdemeler(


    @SerializedName("ct_u_id")val ct_u_id : String,
    @SerializedName("ct_date") val ct_date: String,
    @SerializedName("ct_bakiye")val ct_bakiye : String,
    @SerializedName("ct_iban")val ct_iban : String,
    @SerializedName("ct_red_sebep")val ct_red_sebep : String,

    )
