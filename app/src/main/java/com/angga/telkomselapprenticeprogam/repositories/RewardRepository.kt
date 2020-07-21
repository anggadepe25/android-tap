package com.angga.telkomselapprenticeprogam.repositories

import com.angga.telkomselapprenticeprogam.models.Reward
import com.angga.telkomselapprenticeprogam.utilities.WrappedListResponse
import com.angga.telkomselapprenticeprogam.webservices.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardRepository (private val api : ApiService){

    fun getRewards(token : String, result : (List<Reward>?, Error?)-> Unit){
        api.getRewards(token).enqueue(object : Callback<WrappedListResponse<Reward>>{
            override fun onFailure(call: Call<WrappedListResponse<Reward>>, t: Throwable) {
                println(t.message)
                result(null, Error(t.message))
            }

            override fun onResponse(call: Call<WrappedListResponse<Reward>>, response: Response<WrappedListResponse<Reward>>) {
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