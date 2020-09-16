package com.example.newsapidemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.newsapidemo.R
import com.example.newsapidemo.adapters.AdapterNews
import com.example.newsapidemo.models.Article
import com.example.newsapidemo.models.NewsResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mList: ArrayList<Article> = ArrayList()
    var adapterNews: AdapterNews? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        getData()
        adapterNews = AdapterNews(this, mList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterNews
    }

    private fun getData(){
        val apiKey= ""
        val url =
            "http://newsapi.org/v2/everything?q=bitcoin&from=2020-08-16&sortBy=publishedAt&apiKey=$apiKey"
        val requestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url,
                {
                    var gson = Gson()
                    var newsResult = gson.fromJson(it, NewsResult::class.java)
                    mList.addAll(newsResult.articles)
                    adapterNews?.setData(mList)
                    progress_bar.visibility = View.GONE
                }, {
            it.message?.let { it1 -> Log.d("abc", it1) }
        })
        requestQueue.add(request)
    }
}