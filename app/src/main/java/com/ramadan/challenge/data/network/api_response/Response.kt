package com.ramadan.challenge.data.network.api_response

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("venues") val venues: List<Venues>
)
