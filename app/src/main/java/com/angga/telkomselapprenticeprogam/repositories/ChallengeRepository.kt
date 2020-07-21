package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.Challenge
import com.angga.telkomselapprenticeprogam.utilities.WrappedResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeRepository (private val api : ApiService){

    fun challenge(token : String, id_program : Int, link : String, result : (Boolean, Error?)-> Unit){
        api.challenge(token, id_program, link).enqueue(object : Callback<WrappedResponse<Challenge>>{
            override fun onFailure(call: Call<WrappedResponse<Challenge>>, t: Throwable) {
                result(false, Error(t.message))
            }

            override fun onResponse(call: Call<WrappedResponse<Challenge>>, response: Response<WrappedResponse<Challenge>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        result(true, null)
                    }else{
                        result(false, Error(body.message))
                    }
                }else{
                    result(false, Error(response.message()))
                }
            }

        })
    }
}