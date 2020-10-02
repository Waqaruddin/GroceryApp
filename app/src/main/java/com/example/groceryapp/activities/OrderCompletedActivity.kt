package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.groceryapp.R

class OrderCompletedActivity : AppCompatActivity() {
    private val delayedTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_completed)

        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, delayedTime)
    }


}