package com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig

import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.ApiResponse
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.LoginResponse
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceInterface {
    @FormUrlEncoded
    @POST("register")
    fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") header: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories")
    fun getStoriesWithLocation(
        @Header("Authorization") header: String,
        @Query("location") location: Int
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun uploadStories(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ApiResponse>

}