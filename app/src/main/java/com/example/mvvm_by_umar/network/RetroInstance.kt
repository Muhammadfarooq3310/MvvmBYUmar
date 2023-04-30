package com.example.mvvm_by_umar.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {
    /**Api base Url**/
    private const val BASE_URL = "https://cat-fact.herokuapp.com/"


    private var retrofit: Retrofit? = null

    fun getRetroClient(): Retrofit {

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}