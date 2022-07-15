package com.thegarage.alphabetrium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        const val NAME = "name"
    }

    // creating variable for searchview
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()

                val context = this@MainActivity
                val intent = Intent(context, SearchResultsActivity::class.java)
                intent.putExtra(NAME, query)
                context.startActivity(intent)

                return false
            }
        })
    }

}