package com.bootcamp.tugas3_bootcampidn.api

import com.bootcamp.tugas3_bootcampidn.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getNews(@Query("country") country: String, @Query("apikey") apikey: String): Call<Response>

}