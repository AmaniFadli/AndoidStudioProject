package com.example.retrofitpeliculas

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpeliculas.databinding.FilmLayoutBinding
import com.example.retrofitpeliculas.data.model.Result
import com.bumptech.glide.Glide

class MoviesViewHolder (view : View) : RecyclerView.ViewHolder(view) {

    val filmTitle = view.findViewById<TextView>(R.id.mvTitle)
    val filmYear = view.findViewById<TextView>(R.id.mvYear)
    val filmPoster = view.findViewById<ImageView>(R.id.poster)

    fun render(films: Result){
        filmTitle.text = films.title
        filmYear.text = films.release_date
        Glide.with(filmPoster.context).load(films.poster_path).into(filmPoster)
    }
}