package com.example.retrofitpeliculas

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofitpeliculas.data.RetrofitServiceFacroty
import com.example.retrofitpeliculas.data.model.Result
import com.example.retrofitpeliculas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.retrofitpeliculas.data.MovieDbHelper
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val service = RetrofitServiceFacroty.makeRetrofitService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecyclerView()

        binding.buttonWatched.setOnClickListener{

            val intent = Intent(this, WatchedMoviesActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showRecyclerView(){
        val decoration = DividerItemDecoration(this,GridLayoutManager(this, 2).orientation )
        val recyclerView = binding.recyclerViewFilms

        recyclerView.layoutManager =  GridLayoutManager(this, 2)

        recyclerView.addItemDecoration(decoration)
        CoroutineScope(Dispatchers.IO).launch {
            val filmsList = showMovies()
            runOnUiThread {
                recyclerView.adapter = RecyclerViewAdapter(filmsList)
            }
        }
    }
    private suspend fun showMovies() : List<Result> {
        val filmsList = mutableListOf<Result>()

        try {
            val movies = service.listPopularMovies("9adf2368890d9ce6d99d6aa742e07209", "US")
            println(movies)

            for (movie in movies.results) {
                val peli = Result(
                    title = movie.title,
                    release_date = movie.release_date.substring(0, 4),
                    overview = movie.overview,
                    poster_path = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${movie.poster_path}"
                )
                filmsList.add(peli)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filmsList
    }
}
