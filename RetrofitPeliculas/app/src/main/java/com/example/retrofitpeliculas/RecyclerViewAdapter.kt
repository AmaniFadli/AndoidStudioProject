package com.example.retrofitpeliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.example.retrofitpeliculas.data.model.Result

class RecyclerViewAdapter(var films : List<Result>) : RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.film_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = films[position]
        holder.render(item)

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            //Toast.makeText(context, item.overview ?: "No synopsis available.", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, MoveData::class.java)
            intent.putExtra("script", item.overview)
            //nuevo
            intent.putExtra("photo", item.poster_path)
            intent.putExtra("title", item.title)
            intent.putExtra("release", item.release_date)
            context.startActivity(intent)
        }
    }
}