package com.example.retrofitpeliculas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.retrofitpeliculas.data.model.Result

class MovieDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Movies.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_MOVIE_TABLE = """
            CREATE TABLE IF NOT EXISTS movies (
                title TEXT NOT NULL PRIMARY KEY,
                release_date TEXT,
                overview TEXT,
                poster_path TEXT,
                watched TEXT DEFAULT "0"
            );
        """

        private const val INSERT_MOVIE_STATEMENT = "INSERT INTO movies (title, release_date, overview, poster_path, watched) VALUES (?, ?, ?, ?,?)"

        private const val SELECT_WATCHED_MOVIES_STATEMENT = "SELECT * FROM movies WHERE watched = 1"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MovieDbHelper.CREATE_MOVIE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implementar la lógica para actualizar la base de datos si es necesario
    }

    fun saveMovie(title: String, release_date :String, overview: String,poster_path:String) {
        val writableDatabase = writableDatabase
        writableDatabase.beginTransaction()
        try {
            val insertStatement = writableDatabase.compileStatement(INSERT_MOVIE_STATEMENT)

            // Verificar si la película ya existe
            val cursor = writableDatabase.rawQuery("SELECT * FROM movies WHERE title = ?", arrayOf(title))
            val movieExists = cursor.count > 0
            cursor.close()

            // Insertar solo si la película no existe
            if (!movieExists) {
                insertStatement.bindString(1, title)
                insertStatement.bindString(2, release_date)
                insertStatement.bindString(3, overview)
                insertStatement.bindString(4, poster_path)
                insertStatement.bindString(5,1.toString())
                insertStatement.executeInsert()
                writableDatabase.setTransactionSuccessful()
            } else {
                // Si la película ya existe, puedes manejarlo de alguna manera (p. ej. mostrar un mensaje de error)
                Log.d("MovieDbHelper", "La película ya existe en la base de datos.")
            }
        } finally {
            writableDatabase.endTransaction()
            writableDatabase.close()
        }
    }

    fun getWatchedMovies(): List<Result> {
        val readableDatabase = readableDatabase
        val cursor = readableDatabase.rawQuery(MovieDbHelper.SELECT_WATCHED_MOVIES_STATEMENT, null)
        val movies = mutableListOf<Result>()

        while (cursor.moveToNext()) {
            val title = cursor.getString(0)
            val release_date = cursor.getString(1)
            val overview = cursor.getString(2)
            val poster_path = cursor.getString(3)
            movies.add(Result(title,release_date, overview,poster_path))
        }

        cursor.close()
        readableDatabase.close()
        return movies
    }

}

