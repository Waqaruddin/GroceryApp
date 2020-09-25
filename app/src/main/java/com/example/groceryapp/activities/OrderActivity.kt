package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints.Companion.getAddress
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.helpers.SessionManagerAddress
import com.example.groceryapp.models.MyAddress
import kotlinx.android.synthetic.main.activity_submit_order.*

class OrderActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var sessionManagerAddress: SessionManagerAddress
   // var address:MyAddress? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)
        sessionManager = SessionManager(this)
       sessionManagerAddress = SessionManagerAddress(this)
        val dbHelper = DBHelper(this)

        init()
    }

    private fun init() {
        var address = sessionManagerAddress.getAddress()

        text_view_city.text = address.city
        text_view_pincode.text = address.pincode.toString()
        text_view_our_house_no.text = address.houseNo
        text_view_street_name.text = address.streetName


        button_submit_order.setOnClickListener {
            var userId = sessionManager.getUserId()
        }
    }

    private fun getAddressInfo() {
//        text_view_city.text = address?.city
//        text_view_our_house_no.text = address?.houseNo
//        text_view_pincode.text = address?.pincode.toString()
    }
}