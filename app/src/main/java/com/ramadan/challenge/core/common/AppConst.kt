package com.ramadan.challenge.core.common

object AppConst {
    // Not secure : CLIENT_ID & CLIENT_SECRET should be in native module for security as a best practice
    const val CLIENT_ID_VALUE = "YOUR CLIENT ID"
    const val CLIENT_SECRET_VALUE = "YOUR CLIENT SECRET"
    // APP
    const val BASE_URL = "https://api.foursquare.com/"
    const val TIMEOUT_REQUEST = 20L
    // keys
    const val DEFAULT_QUERY = "restaurant"
    const val QUERY = "query"
    const val VERSION = "v"
    const val CLIENT_ID = "client_id"
    const val CLIENT_SECRET = "client_secret"
}
