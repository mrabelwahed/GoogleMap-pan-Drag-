package com.ramadan.challenge.data.network.api_response

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("response") val response: Response
)
