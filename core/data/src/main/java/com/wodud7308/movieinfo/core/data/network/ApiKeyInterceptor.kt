package com.wodud7308.movieinfo.core.data.network

import com.wodud7308.movieinfo.core.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val originUrl = origin.url

        val newUrl = originUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val new = origin.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(new)
    }
}
