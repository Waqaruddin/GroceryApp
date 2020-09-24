package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        init()
    }

    private fun init() {
        button_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }


}