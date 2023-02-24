package com.faiz.faizbastomi_mealdb.data.network.api

import com.faiz.faizbastomi_mealdb.data.network.model.MealDetail
import com.faiz.faizbastomi_mealdb.data.network.model.MealResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("filter.php")
    suspend fun searchMeals(@Query("c") category: String): Response<MealResult>

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String): Response<MealDetail>
}