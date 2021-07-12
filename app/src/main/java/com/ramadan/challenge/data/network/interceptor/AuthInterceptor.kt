package com.ramadan.challenge.data.network.interceptor

import com.ramadan.challenge.core.common.AppConst
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl: HttpUrl = original.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter(AppConst.CLIENT_ID, AppConst.CLIENT_ID_VALUE)
            .addQueryParameter(AppConst.CLIENT_SECRET, AppConst.CLIENT_SECRET_VALUE)
            .addQueryParameter(AppConst.VERSION, "20210709")
            .addQueryParameter(AppConst.QUERY, AppConst.DEFAULT_QUERY)

            .build()
        val requestBuilder: Request.Builder = original.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
