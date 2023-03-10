package com.faiz.faizbastomi_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.faiz.faizbastomi_mealdb.adapter.FavoriteAdapter
import com.faiz.faizbastomi_mealdb.data.database.MealEntity
import com.faiz.faizbastomi_mealdb.databinding.ActivityFavoriteBinding
import com.faiz.faizbastomi_mealdb.viewmodels.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.apply {
            title = "Favorite"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#3282B8")))
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteMealList.observe(this) { result ->
            if (result.isEmpty()) {
                binding.apply {
                    rvFavoriteMeal.isVisible = false
                    emptyDesc.isVisible = true
                    emptyText.isVisible = true
                    deleteWrapper.isVisible = false
                }
            } else {
                binding.apply {
                    rvFavoriteMeal.apply {
                        adapter = favoriteAdapter
                        layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    }
                    deleteAll.setOnClickListener {
                        deleteAllFavorites()
                    }
                }
                favoriteAdapter.apply {
                    setData(result)
                    setOnItemClickCallback(object : FavoriteAdapter.IOnFavoriteItemCallBack {
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val intent = Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                            intent.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_MEAL, data)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    private fun deleteAllFavorites() {
        favoriteViewModel.deleteAllMeals()
        Toast.makeText(this, "Successfully delete all bookmark(s)", Toast.LENGTH_SHORT).show()
    }
}