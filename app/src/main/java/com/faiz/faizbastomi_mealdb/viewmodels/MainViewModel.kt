package com.faiz.faizbastomi_mealdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faiz.faizbastomi_mealdb.data.RemoteDataSource
import com.faiz.faizbastomi_mealdb.data.Repository
import com.faiz.faizbastomi_mealdb.data.network.Service
import com.faiz.faizbastomi_mealdb.data.network.handler.NetworkResult
import com.faiz.faizbastomi_mealdb.data.network.model.MealResult
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private val remoteService = Service.mealService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _mealsList: MutableLiveData<NetworkResult<MealResult>> = MutableLiveData()
    val mealsList: LiveData<NetworkResult<MealResult>> = _mealsList

    init {
        fetchMeals()
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            repository.remote!!.getMeals().collect { res -> _mealsList.value = res }
        }
    }
}