package com.example.mynews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), newsClicked, onCategoryClick {
   private lateinit var mAdapter : newsAdapter
   private lateinit var categoryAdapter : categoryAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // code for news recycler view adapter
          recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
         mAdapter = newsAdapter(this)
        val url = "https://newsdata.io/api/1/news?apikey=pub_18598676b613f9c0498adf09946aa6889da6b&language=en&category=sports"
        fetchData(url)
        recyclerView.adapter = mAdapter

        // code for category recycler view adapter
        val mlayoutManager = LinearLayoutManager(this)
        mlayoutManager.orientation = LinearLayoutManager.HORIZONTAL
       recyclerCategory.layoutManager = mlayoutManager
        val categoryArray = ArrayList<String>()
        categoryArray.add("Sports")
        categoryArray.add("Technology")
        categoryArray.add("Politics")
        categoryArray.add("Entertainment")
        categoryArray.add("Health")
        categoryArray.add("Business")
        categoryArray.add("Environment")
        categoryArray.add("Food")
        categoryArray.add("science")



        categoryAdapter = categoryAdapter(categoryArray,this)
        recyclerCategory.adapter = categoryAdapter

        // code for pull to refresh
        swipeRefreshLayout = findViewById(R.id.refreshView);
        swipeRefreshLayout.setOnRefreshListener {
            // on below line we are setting is refreshing to false.
                swipeRefreshLayout.isRefreshing = false
        }

    }


    // function to get data from the api
    private fun fetchData(url : String) {
   val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObjectArray = response.getJSONArray("results")

                val newsArray = ArrayList<news>()

                for(i in 0 until jsonObjectArray.length())
                {

                    val jsonObject = jsonObjectArray.getJSONObject(i)
                    Log.d("hiii",jsonObject.getString("link"))

                    val newsObject = news(
                        jsonObject.getString("title"),
                        jsonObject.getString("link"),
                        jsonObject.getString("source_id"),
                        jsonObject.getString("image_url")
                    )

                    newsArray.add(newsObject)

                }
                mAdapter.updateNews(newsArray)

            },
             { error ->
     Log.d("hiiii","hello")
            }

        )
        // add the request to the requestQueue
        queue.add(jsonObjectRequest)
      //  singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


//  function to handle the news click
    override fun onNewsClicked(news: news) {
        val  url = news.url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onCategoryClicked(category: String) {
        Log.d("category",category)
        val url = "https://newsdata.io/api/1/news?apikey=pub_18598676b613f9c0498adf09946aa6889da6b&language=en&category="+category
        fetchData(url)
        recyclerView.scrollToPosition(0)
    }
}