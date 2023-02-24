package com.faiz.faizbastomi_mealdb.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.faiz.faizbastomi_mealdb.R

object BindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.ic_launcher_background
        Glide.with(imageView.context)
            .load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}