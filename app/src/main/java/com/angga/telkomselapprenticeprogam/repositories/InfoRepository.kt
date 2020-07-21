package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.Info
import com.angga.telkomselapprenticeprogam.utilities.WrappedListResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoRepository (private val api : ApiService){
    fun getInfos(token : String, result :(List<Info>?, Error?)->Unit){
        api.getInfos(token).enqueue(object : Callback<WrappedListResponse<Info>>{
            override fun onFailure(call: Call<WrappedListResponse<Info>>, t: Throwable) = result(null, Error(t.message))

            override fun onResponse(call: Call<WrappedListResponse<Info>>, response: Response<WrappedListResponse<Info>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        val data = body.data
                        result(data, null)
                    }else{
                        result(null, Error())
                    }
                }else{
                    result(null, Error(response.message()))
                }
            }

        })
    }
}