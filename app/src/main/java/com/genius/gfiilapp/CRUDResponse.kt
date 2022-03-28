package com.genius.gfiilapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class CRUDResponse(
    @SerializedName("status")  var status: String,
    @SerializedName("result_code")  var result_code: Int,
    @SerializedName("response")  var response: String

)
