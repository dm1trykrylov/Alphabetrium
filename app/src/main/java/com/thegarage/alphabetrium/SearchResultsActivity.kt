package com.thegarage.alphabetrium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.*
import java.io.IOException;

class SearchResultsActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    val baseRMurl = "https://rickandmortyapi.com/api/character/?name="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val name = intent?.extras?.getString(MainActivity.NAME).toString()

        run(baseRMurl + name)
        /*
        make GET request to RickAndMorty API
        and
        parse JSON
         */

    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
}