package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.SingleResponse
import com.angga.telkomselapprenticeprogam.utilities.WrappedResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


interface UserContract{
    fun updateProfile(token: String, user: User, listener : SingleResponse<User>)
    fun updatePhoto(token: String, urlImg : String, listener: SingleResponse<User>)
    fun updatePassword(token: String, password: String, listener: SingleResponse<User>)
}

class UserRepository (private val api : ApiService) : UserContract{
    override fun updatePassword(token: String, password: String, listener: SingleResponse<User>) {
        api.updatePassword(token, password).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> {
                        val body = response.body()
                        if (body?.status!!){
                            listener.onSuccess(body.data)
                        }else{
                            listener.onFailure(Error(body.message))
                        }
                    }
                    !response.isSuccessful -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }

    override fun updateProfile(token: String, user: User, listener: SingleResponse<User>) {
        val g = GsonBuilder().create()
        val json = g.toJson(user)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        api.updateProfile(token, body).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b?.status!!){
                            listener.onSuccess(b.data)
                        }else{
                            listener.onFailure(Error(b.message))
                        }
                    }
                    !response.isSuccessful -> listener.onFailure(Error(response.message()))
                }
            }

        })
    }

    override fun updatePhoto(token: String, urlImg: String, listener: SingleResponse<User>) {
        val file = File(urlImg)
        val requestBodyForFile = RequestBody.create(MediaType.parse("image/*"), file)
        val image = MultipartBody.Part.createFormData("foto", file.name, requestBodyForFile)
        api.updatePhotoProfile(token, image).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b?.status!!){
                            listener.onSuccess(b.data)
                        }else{
                            listener.onFailure(Error(b.message))
                        }
                    }
                    !response.isSuccessful -> listener.onFailure(Error(response.message()))
                }
            }

        })


    }

    fun login(email : String, password : String, result : (String?, Error?)->Unit){
        api.login(email, password).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                result(null, Error(t.message))
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        result(body.data!!.token, null)
                    }else{
                        result(null, Error("maaf, gagal login"))
                    }
                }else{
                    result(null, Error("gagal login"))
                }
            }
        })
    }

    fun profile(token : String, result: (User?, Error?) -> Unit){
        api.profile(token).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                result(null, Error(t.message))
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                when{
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b?.status!!){
                            result(b.data, null)
                        }else{
                            result(null, Error())
                        }
                    }
                    !response.isSuccessful -> result(null, Error(response.message()))
                }
            }

        })
    }
}