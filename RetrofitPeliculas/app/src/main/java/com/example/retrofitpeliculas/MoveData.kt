package com.example.retrofitpeliculas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.example.retrofitpeliculas.data.MovieDbHelper

class MoveData  : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_data_layout)
        val btnBack: Button = findViewById(R.id.backButton)

        val script = intent.getStringExtra("script")
        val tvScript: TextView = findViewById(R.id.synopsisData)

        val image: ImageView = findViewById(R.id.imagePoster)
        val imageId = intent.getStringExtra("photo")
        Glide.with(image.context).load(imageId).into(image)

        val title = intent.getStringExtra("title")
        val release = intent.getStringExtra("release")
        tvScript.text = script
        btnBack.setOnClickListener{finish()}

        val buttonWatch = findViewById<ImageButton>(R.id.buttonVist)

        buttonWatch.setOnClickListener {
            val dbHelper = MovieDbHelper(this)
            if (title != null && release != null && script != null && imageId != null) {
                dbHelper.saveMovie(title, release, script, imageId)
                Toast.makeText(this, "¡guardado", Toast.LENGTH_SHORT).show()
            }
            dbHelper.close()
            //Toast.makeText(this, "¡Película marcada como vista!", Toast.LENGTH_SHORT).show()
        }
    }

}