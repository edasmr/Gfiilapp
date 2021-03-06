package com.genius.gfiilapp

import com.google.gson.annotations.SerializedName

data class CoinModel(

    @SerializedName("id")val id : String,
    @SerializedName("name")val name : String,
    @SerializedName("symbol")val symbol : String,
  //  @SerializedName("rank")val rank : String,
    @SerializedName("price_usd")val price_usd : String,
   // @SerializedName("price_btc")val price_btc : String,
  //  @SerializedName("24_volume_usd")val volume_usd : String,
 //   @SerializedName("market_cap_usd")val market_cap_usd : String,
 //   @SerializedName("avaliable_supply")val avaliable_supply : String,
    @SerializedName("percent_change_1h")val percent_change_1h : String,
    @SerializedName("percent_change_24h")val percent_change_24h : String,
    @SerializedName("percent_change_7d")val percent_change_7d : String,
  //  @SerializedName("last_updated")val last_updated : String,


    )
