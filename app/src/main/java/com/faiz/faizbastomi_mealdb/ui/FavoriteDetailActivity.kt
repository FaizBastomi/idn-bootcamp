package com.faiz.faizbastomi_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.faiz.faizbastomi_mealdb.R
import com.faiz.faizbastomi_mealdb.data.database.MealEntity
import com.faiz.faizbastomi_mealdb.databinding.ActivityFavoriteDetailBinding
import com.faiz.faizbastomi_mealdb.viewmodels.FavoriteDetailViewModel

class FavoriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDetailBinding
    private val favoriteDetailViewModel by viewModels<FavoriteDetailViewModel>()

    companion object {
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.apply {
            title = "Meal Detail"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#3282B8")))
            setDisplayHomeAsUpEnabled(true)
        }

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)
        binding.mealDetail = favoriteMeal!!.meal

        binding.apply {
            bookmarkBtn.apply {
                setImageResource(R.drawable.ic_bookmark_added)
                setOnClickListener {
                    deleteFavoriteMeal(favoriteMeal)
                    val intent = Intent(this@FavoriteDetailActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            watchYt.setOnClickListener {
                openWeb(favoriteMeal.meal.strYoutube)
            }
        }
    }

    private fun openWeb(url: String?) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun deleteFavoriteMeal(mealEntity: MealEntity) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from bookmark(s)", Toast.LENGTH_SHORT).show()
    }
}