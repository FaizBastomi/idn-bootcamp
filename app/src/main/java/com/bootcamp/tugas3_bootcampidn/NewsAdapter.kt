package com.bootcamp.tugas3_bootcampidn

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.tugas3_bootcampidn.databinding.ItemRowNewsBinding
import com.bootcamp.tugas3_bootcampidn.model.ArticlesItem
import com.bumptech.glide.Glide
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class NewsAdapter() :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var dataNews: List<ArticlesItem> = listOf()

    inner class NewsViewHolder(private val binding: ItemRowNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = LocalDate.parse(data.publishedAt, formatter)
            val time = LocalTime.parse(data.publishedAt, formatter)
            binding.apply {
                tvJudul.text = data.title
                tvPenulis.text = data.author
                tvTanggalPosting.text = "$date, $time"
                binding.cardNews.setOnClickListener {
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    intent.putExtra(DetailNewsActivity.EXTRA_NEWS, data)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(binding.imgNews)
                .load(data.urlToImage)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgNews)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = ItemRowNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        return holder.bind(dataNews[position])
    }

    fun setData(data: List<ArticlesItem>) {
        dataNews = data
        notifyDataSetChanged()
    }
}