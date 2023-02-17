package com.bootcamp.tugas3_bootcampidn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bootcamp.tugas3_bootcampidn.databinding.ActivityDetailNewsBinding
import com.bootcamp.tugas3_bootcampidn.model.ArticlesItem
import com.bumptech.glide.Glide

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = intent.getParcelableExtra<ArticlesItem>(EXTRA_NEWS)

        binding.apply {
            tvJudul.text = news?.title
            tvDeskripsi.text = news?.description
            btnShare.setOnClickListener {
                shareDescription(news?.description)
            }
        }
        Glide.with(binding.imgNews)
            .load(news?.urlToImage)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imgNews)
    }

    private fun shareDescription(description: String?) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_WEB_SEARCH
            putExtra(Intent.EXTRA_TEXT, description)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, "Share Description")
        startActivity(shareIntent)
    }

    companion object {
        const val EXTRA_NEWS = "news"
    }
}