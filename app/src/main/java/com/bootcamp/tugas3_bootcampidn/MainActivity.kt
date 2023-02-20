package com.bootcamp.tugas3_bootcampidn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.tugas3_bootcampidn.adapter.NewsAdapter
import com.bootcamp.tugas3_bootcampidn.api.ApiConfig
import com.bootcamp.tugas3_bootcampidn.databinding.ActivityMainBinding
import com.bootcamp.tugas3_bootcampidn.model.ArticlesItem
import com.bootcamp.tugas3_bootcampidn.model.Response
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ApiConfig.getService().getNews("id", "f044de69086e45198f8406be2094a229")
            .enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response.isSuccessful) {
                        val responseNews = response.body()
                        val dataNews = responseNews?.articles
                        val newsAdapter = NewsAdapter()
                        newsAdapter.setData(dataNews as List<ArticlesItem>)
                        binding.rvNews.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            setHasFixedSize(true)
                            adapter = newsAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("gagal", "onFailure: " + t.localizedMessage)
                }

            })
    }
}