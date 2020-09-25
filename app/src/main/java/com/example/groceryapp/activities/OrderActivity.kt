package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.MyAddress
import kotlinx.android.synthetic.main.activity_submit_order.*

class OrderActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var address:MyAddress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)
        address = intent.getSerializableExtra(MyAddress.KEY_ADDRESS) as MyAddress
        sessionManager = SessionManager(this)

        init()
    }

    private fun init() {

        button_submit_order.setOnClickListener {

            var userId = sessionManager.getUserId()


        }



    }
}