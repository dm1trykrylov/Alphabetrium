package com.thegarage.alphabetrium

import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import okhttp3.*
import java.io.IOException

class SearchResultsActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private val baseURL = "https://rickandmortyapi.com/api/character/?name="
    lateinit var requestName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val name = intent?.extras?.getString(MainActivity.NAME).toString()

        val response = run(baseURL + name)

        val result = Klaxon().parse<RMRequest>(response)

        requestName = findViewById(R.id.requestName)

        requestName.text = result!!.info.count.toString()
    }

    @Throws(IOException::class)
    fun run(url: String): String {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }
}