package com.example.retrofitpeliculas

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitpeliculas.data.MovieDbHelper
import com.example.retrofitpeliculas.data.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchedMoviesActivity : AppCompatActivity() {

    //private lateinit var watchedMoviesAdapter: WatchedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watched_recyclerview)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewWatchedMovies)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val decoration = DividerItemDecoration(this, GridLayoutManager(this, 2).orientation )
        recyclerView.addItemDecoration(decoration)

        CoroutineScope(Dispatchers.IO).launch {
            val filmsList = showMovies()
            runOnUiThread {
                recyclerView.adapter = RecyclerViewAdapter(filmsList)
            }
        }

        val btnBack: Button = findViewById(R.id.btnBackToMenu)
        btnBack.setOnClickListener{finish()}
    }
    private suspend fun showMovies() : List<Result> {
        val filmsList = mutableListOf<Result>()
        var movies = mutableListOf<Result>()
        try {
            val dbHelper = MovieDbHelper(applicationContext)
            movies = dbHelper.getWatchedMovies().toMutableList()

            println(movies)

            for (movie in movies) {
                val peli = Result(
                    title = movie.title,
                    release_date = movie.release_date.substring(0, 4),
                    overview = movie.overview,
                    poster_path = movie.poster_path
                )
                filmsList.add(peli)
            }
            dbHelper.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return movies
    }
}