package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager

class StartActivity : AppCompatActivity() {

    private val delayedTime: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, delayedTime)
    }

    private fun checkLogin() {
        var sessionManager = SessionManager(this)
        if(sessionManager.isLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()

        }
    }
}