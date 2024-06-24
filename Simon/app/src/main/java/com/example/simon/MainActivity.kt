package com.example.simon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var random = (0..3).random()
        val fourColors:Array<String> = arrayOf("Green", "Yellow", "Blue", "Red")
        val allColors:ArrayList<String> = arrayListOf(fourColors[random])
        val start: Button = findViewById<Button>(R.id.startBtn)
        val activitiesArray = arrayOf(Green::class.java, Yellow::class.java, Red::class.java, Blue::class.java)

        for(i:Int in 0..3){
            random = (0..3).random()
            allColors.add(fourColors[random])
        }

        start.setOnClickListener{
            val intent = Intent(this@MainActivity, activitiesArray[random])
            intent.putStringArrayListExtra("colors", allColors)
            intent.putExtra("count",0)
            intent.putExtra("score",0)
            startActivity(intent)
        }
    }
}