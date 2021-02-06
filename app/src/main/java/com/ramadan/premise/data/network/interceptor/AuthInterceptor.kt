package com.ramadan.premise.data.network.interceptor

import com.ramadan.premise.util.AppConst
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl: HttpUrl = original.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter(AppConst.ACCESS_KEY, AppConst.WEATHER_API_KEY)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
