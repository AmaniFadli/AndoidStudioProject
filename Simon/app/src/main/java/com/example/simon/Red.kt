package com.example.simon

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.ArrayList

class Red : AppCompatActivity() {
    private var soundPool: SoundPool? = null
    private var greenSoundId = 0
    private var yellowSoundId = 0
    private var blueSoundId = 0
    private var redSoundId = 0
    private var logroSoundId = 0
    private var errorSoundId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red)

        soundPool = SoundPool.Builder()
            .setMaxStreams(4) // Podemos tener un máximo de 4 sonidos simultáneos
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build())
            .build()
        greenSoundId = soundPool?.load(this, R.raw.b1, 1) ?: 0
        yellowSoundId = soundPool?.load(this, R.raw.b2, 1) ?: 0
        blueSoundId = soundPool?.load(this, R.raw.b3, 1) ?: 0
        redSoundId = soundPool?.load(this, R.raw.b4, 1) ?: 0
        logroSoundId = soundPool?.load(this, R.raw.logro, 1) ?: 0
        errorSoundId = soundPool?.load(this, R.raw.error, 1) ?: 0

        val title = findViewById<TextView>(R.id.titleText)
        val scoreText = findViewById<TextView>(R.id.scoreText)
        val green = findViewById<Button>(R.id.greenBtn)
        val yellow = findViewById<Button>(R.id.yellowBtn)
        val blue = findViewById<Button>(R.id.blueBtn)
        val red = findViewById<Button>(R.id.redBtn)
        val restart = findViewById<Button>(R.id.restartBtn)
        val activitiesArray = arrayOf(Green::class.java, Yellow::class.java, Red::class.java, Blue::class.java)

        var score = intent.getIntExtra("score", -2)
        var count = intent.getIntExtra("count", -3)
        val colors: ArrayList<String>? = intent.getStringArrayListExtra("colors")

        scoreText.text = score.toString()

        if(score!= count){
            title.text = "Color:" +(count + 1)
        }
        else{
            title.text = "Simon says: " + colors!![count]
        }

        fun gameOver(newTitle:String){
            colors!![count] = newTitle
            title.text = newTitle
            restart.visibility = View.VISIBLE
            red.text = newTitle
            yellow.text = newTitle
            green.text = newTitle
        }
        fun onCorrect(answer:String, classNum:Int){
            if(colors!![count] == answer){
                val intent = Intent(this@Red,activitiesArray[classNum])
                if(count+1 == colors!!.size){
                    gameOver("YOU WIN!")
                    soundPool?.play(logroSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
                }
                else{
                    if(count == score){
                        count = -1
                        score++
                    }
                    count++
                    intent.putStringArrayListExtra("colors", colors)
                    intent.putExtra("count",count)
                    intent.putExtra("score",score)
                    startActivity(intent)
                }
            }
            else if(restart.visibility != 0){
                gameOver("GameOver")
                soundPool?.play(errorSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }

        }
        green.setOnClickListener{
            onCorrect("Green",0)
            soundPool?.play(greenSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }

        yellow.setOnClickListener{
            onCorrect("Yellow",1)
            soundPool?.play(yellowSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
        blue.setOnClickListener{
            onCorrect("Blue",2)
            soundPool?.play(blueSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }

        red.setOnClickListener{
            onCorrect("Red",3)
            soundPool?.play(redSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
        restart.setOnClickListener {
            val intent = Intent(this@Red, MainActivity::class.java)
            startActivity(intent)
        }
    }


}