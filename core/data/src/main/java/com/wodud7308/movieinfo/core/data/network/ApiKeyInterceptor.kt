package com.wodud7308.movieinfo.core.data.network

import com.wodud7308.movieinfo.core.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.run {
            return proceed(
                request()
                    .newBuilder()
                    .addHeader("api_key", "Bearer ${BuildConfig.API_KEY}")
                    .build(),
            )
        }
    }
}
