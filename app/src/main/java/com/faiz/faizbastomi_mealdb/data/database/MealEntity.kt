package com.faiz.faizbastomi_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faiz.faizbastomi_mealdb.data.network.model.MealsItem
import com.faiz.faizbastomi_mealdb.util.Constant.MEAL_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = MEAL_TABLE_NAME)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal: MealsItem
) : Parcelable
