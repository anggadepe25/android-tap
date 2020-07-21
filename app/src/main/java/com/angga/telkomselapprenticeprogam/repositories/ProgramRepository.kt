package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.Program
import com.angga.telkomselapprenticeprogam.utilities.WrappedListResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramRepository (private val api : ApiService){
    fun getPrograms(token : String, result : (List<Program>?, Error?)-> Unit){
        api.getPrograms(token).enqueue(object : Callback<WrappedListResponse<Program>>{
            override fun onFailure(call: Call<WrappedListResponse<Program>>, t: Throwable) {
                println(t.message)
                result(null, Error(t.message))
            }

            override fun onResponse(call: Call<WrappedListResponse<Program>>, response: Response<WrappedListResponse<Program>>) {
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