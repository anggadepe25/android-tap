package com.angga.telkomselapprenticeprogam.webservices

import com.angga.telkomselapprenticeprogam.models.*
import com.angga.telkomselapprenticeprogam.utilities.Constants
import com.angga.telkomselapprenticeprogam.utilities.WrappedListResponse
import com.angga.telkomselapprenticeprogam.utilities.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null

        private val opt = OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()

        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().apply {
                    client(opt)
                    baseUrl(Constants.END_POINT)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
                retrofit!!
            } else {
                retrofit!!
            }
        }

        fun instance() = getClient().create(ApiService::class.java)
    }
}

interface ApiService{

    @FormUrlEncoded
    @POST("api/user/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<WrappedResponse<User>>

    @Headers("Content-Type: application/json")
    @POST("api/user/profile/update")
    fun updateProfile(
        @Header("Authorization") token :String,
        @Body body: RequestBody
    ) : Call<WrappedResponse<User>>

    @Multipart
    @POST("api/user/profile/update/photo")
    fun updatePhotoProfile(
        @Header("Authorization") token : String,
        @Part image : MultipartBody.Part
    ) :Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("api/user/profile/update/password")
    fun updatePassword(
        @Header("Authorization") token :String,
        @Field("password") password: String
    ) : Call<WrappedResponse<User>>

    @GET("api/news")
    fun getInfos(
        @Header("Authorization") token :String
    ) : Call<WrappedListResponse<Info>>


    @GET("api/reward")
    fun getRewards(
        @Header("Authorization") token :String
    ) : Call<WrappedListResponse<Reward>>

    @GET("api/program")
    fun getPrograms(
        @Header("Authorization") token :String
    ) : Call<WrappedListResponse<Program>>

    @FormUrlEncoded
    @POST("api/challenge")
    fun challenge(
        @Header("Authorization") token :String,
        @Field("id_program") id_program : Int,
        @Field("link") link : String
    ) : Call<WrappedResponse<Challenge>>

    @GET("api/user/profile")
    fun profile(
        @Header("Authorization") token :String
    ) : Call<WrappedResponse<User>>

}