package com.faiz.faizbastomi_mealdb.data

import android.util.Log
import com.faiz.faizbastomi_mealdb.data.network.api.MealsApi
import com.faiz.faizbastomi_mealdb.data.network.handler.NetworkResult
import com.faiz.faizbastomi_mealdb.data.network.model.MealDetail
import com.faiz.faizbastomi_mealdb.data.network.model.MealResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val MealsApi: MealsApi) {
    suspend fun getMeals(): Flow<NetworkResult<MealResult>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val meals = MealsApi.searchMeals("Seafood")
            if (meals.isSuccessful) {
                val responseBody = meals.body()

                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("Meals not found"))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                Log.d("apiRemoteError", "StatusCode: ${meals.code()}, message: ${meals.message()}")
                emit(NetworkResult.Error("Failed to fetch from server."))
            }
        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealDetail(mealId: String): Flow<NetworkResult<MealDetail>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val meal = MealsApi.getMealDetail(mealId)
            if (meal.isSuccessful) {
                val responseBody = meal.body()

                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("Meals not found"))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                Log.d("apiRemoteError", "StatusCode: ${meal.code()}, message: ${meal.message()}")
                emit(NetworkResult.Error("Failed to fetch from server."))
            }
        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}