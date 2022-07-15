package com.thegarage.alphabetrium

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.thegarage.alphabetrium.adapter.ItemAdapter
import com.thegarage.alphabetrium.data.Datasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.net.URL


class SearchResultsActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private val baseURL = "https://rickandmortyapi.com/api/character/?name="
    private var reqURL = baseURL
    lateinit var requestName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val name = intent?.extras?.getString(MainActivity.NAME).toString()
        requestName = findViewById(R.id.requestName)
        requestName.text = name

        reqURL += name
        //val response = run(reqURL)

        val result = fetch(reqURL)

        if(result != null){
            requestName.text = result.info.count.toString()
        }
        else{
            Toast.makeText(this, "request error", Toast.LENGTH_LONG).show()
        }

        // Initialize data.
        val myDataset = Datasource().loadAffirmations()

        val recyclerView = findViewById<RecyclerView>(R.id.characterList)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }

    /*
    @Throws(IOException::class)
    fun run(url: String): String {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }
     */
    private fun run(sUrl: String): String? {
        var result: String? = null

        try {
            // Create URL
            val url = URL(sUrl)

            // Build request
            val request = Request.Builder().url(sUrl).build()

            // Execute request
            val response = client.newCall(request).execute()
/*
            if (response.body != null) Toast.makeText(this, "request success!", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "error!", Toast.LENGTH_LONG).show()

 */
            result = response.body?.string()

        }
        catch(err:Error) {
            print("Error when executing get request: "+err.localizedMessage)
        }

        return result
    }

    private fun fetch(sUrl: String): RMRequest? {
        var request: RMRequest? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val result = run(sUrl)
            if (result != null) {
                Log.d(TAG, result)
                print(result)
                try {
                    // Parse result string JSON to data class
                    request = Klaxon().parse<RMRequest>(result)

                }
                catch(err:Error) {
                    print("Error when parsing JSON: "+err.localizedMessage)
                }
            }
            else {
                print("Error: Get request returned no response")
            }
        }
        return request
    }
}