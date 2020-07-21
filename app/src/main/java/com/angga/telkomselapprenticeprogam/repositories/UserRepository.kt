package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.User
import com.angga.telkomselapprenticeprogam.utilities.WrappedResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (private val api : ApiService){
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