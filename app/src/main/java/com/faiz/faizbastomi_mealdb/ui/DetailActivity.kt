package com.faiz.faizbastomi_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.faiz.faizbastomi_mealdb.R
import com.faiz.faizbastomi_mealdb.data.database.MealEntity
import com.faiz.faizbastomi_mealdb.data.network.handler.NetworkResult
import com.faiz.faizbastomi_mealdb.data.network.model.MealsItem
import com.faiz.faizbastomi_mealdb.data.network.model.MealsItems
import com.faiz.faizbastomi_mealdb.databinding.ActivityDetailBinding
import com.faiz.faizbastomi_mealdb.viewmodels.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mealDetail: MealsItem
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_MEAL = "meal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Meal Detail"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#3282B8")))
            setDisplayHomeAsUpEnabled(true)
        }

        val meal = intent.getParcelableExtra<MealsItems>(EXTRA_MEAL)

        detailViewModel.fetchMealDetail(meal?.idMeal as String)

        detailViewModel.mealDetail.observe(this) { res ->
            when (res) {
                is NetworkResult.Loading -> {
                    handleUi(wrapper = false, progress = true, errorTv = false)
                }
                is NetworkResult.Error -> {
                    handleUi(wrapper = false, progress = false, errorTv = true)
                }
                is NetworkResult.Success -> {
                    val data = res.data?.meals?.get(0)
                    mealDetail = res.data?.meals?.get(0)!!
                    binding.apply {
                        mealDetail = data
                        watchYt.setOnClickListener {
                            openWeb(data?.strYoutube)
                        }
                    }
                    handleUi(wrapper = true, progress = false, errorTv = false)
                }
            }
        }

        isFavoriteMeal(meal)
    }

    private fun handleUi(
        wrapper: Boolean,
        progress: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = wrapper
            progressBar.isVisible = progress
            errorText.isVisible = errorTv
        }
    }

    private fun isFavoriteMeal(mealSelected: MealsItems) {
        detailViewModel.favoriteMealList.observe(this) { result ->
            val meal = result.find { favorite ->
                favorite.meal.idMeal == mealSelected.idMeal
            }
            if (meal != null) {
                binding.bookmarkBtn.apply {
                    setImageResource(R.drawable.ic_bookmark_added)
                    setOnClickListener {
                        deleteFavoriteMeal(meal.id)
                    }
                }
            } else {
                binding.bookmarkBtn.apply {
                    setImageResource(R.drawable.ic_bookmark_add)
                    setOnClickListener {
                        insertFavoriteMeal()
                    }
                }
            }
        }
    }

    private fun insertFavoriteMeal() {
        val mealEntity = MealEntity(meal = mealDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully add to bookmark(s)", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteMeal(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId, mealDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from bookmark(s)", Toast.LENGTH_SHORT).show()
    }

    private fun openWeb(url: String?) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}