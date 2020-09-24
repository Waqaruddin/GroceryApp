package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager:SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)

        init()
    }

    private fun init() {
        button_save.setOnClickListener{
            var pincode = edit_text_pincode.text.toString()
            var streetName = edit_text_street.text.toString()
            var city = edit_text_city.text.toString()
            var houseNo = edit_text_house.text.toString()
            var type = edit_text_type.text.toString()
            var userId = sessionManager.getUserId()
            var params = HashMap <String, Any>()
            params["pincode"] = pincode.toInt()
            params["streetName"] = streetName
            params["city"] = city
            params["houseNo"] = houseNo
            params["type"] = type
            params["userId"] = userId
            Log.d("abc", userId)

            var jsonObject = JSONObject(params as Map <*, *>)
            var request = JsonObjectRequest(
                Request.Method.POST, Endpoints.saveAddress(), jsonObject,
                {
                    Toast.makeText(applicationContext, "Address Saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AddressActivity::class.java))
                },
                {

                }
            )
            Volley.newRequestQueue(this).add(request)
            //startActivity(Intent(this, AddressActivity::class.java))
            finish()

        }
    }
}