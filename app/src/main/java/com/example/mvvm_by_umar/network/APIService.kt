package com.example.mvvm_by_umar.network

import com.example.mvvm_by_umar.model.FectModel
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    /**Api  Url End Point**/
    @GET("facts")
    fun getList(): Call<List<FectModel>>

}