package com.ramadan.challenge.data.network.response

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("disclaimer") val disclaimer: String,
    @SerializedName("license") val license: String,
    @SerializedName("timestamp") val timestamp: Int,
    @SerializedName("base") val base: String,
    @SerializedName("rates") val ratesObj: HashMap<String, Double>
)
