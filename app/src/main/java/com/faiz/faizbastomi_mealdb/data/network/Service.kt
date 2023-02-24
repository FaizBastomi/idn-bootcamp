package com.faiz.faizbastomi_mealdb.data.network

import com.faiz.faizbastomi_mealdb.data.network.api.MealsApi
import com.faiz.faizbastomi_mealdb.util.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mealService: MealsApi by lazy {
        retrofit.create(MealsApi::class.java)
    }
}