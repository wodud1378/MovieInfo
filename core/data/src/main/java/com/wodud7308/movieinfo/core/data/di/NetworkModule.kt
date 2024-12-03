package com.wodud7308.movieinfo.core.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import com.wodud7308.movieinfo.core.data.common.Constants
import com.wodud7308.movieinfo.core.data.model.ContentModel
import com.wodud7308.movieinfo.core.data.model.MovieApiModel
import com.wodud7308.movieinfo.core.data.model.TvShowApiModel
import com.wodud7308.movieinfo.core.data.network.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ApiKeyInterceptor())
            .build()

    @Singleton
    @Provides
    fun provideGson(customDeserializer: JsonDeserializer<ContentModel>): Gson =
        GsonBuilder()
            .registerTypeAdapter(ContentModel::class.java, customDeserializer)
            .create()

    @Singleton
    @Provides
    fun provideCustomDeserializer(): JsonDeserializer<ContentModel> =
        JsonDeserializer<ContentModel> { json, _, context ->
            val jsonObject = json.asJsonObject
            try {
                if (jsonObject.has("title")) {
                    ContentModel(
                        context.deserialize<MovieApiModel>(
                            jsonObject,
                            MovieApiModel::class.java
                        )
                    )
                } else {
                    ContentModel(
                        context.deserialize<TvShowApiModel>(
                            jsonObject,
                            TvShowApiModel::class.java
                        )
                    )
                }
            } catch (e: Exception) {
                throw JsonParseException(e.toString())
            }
        }
}
