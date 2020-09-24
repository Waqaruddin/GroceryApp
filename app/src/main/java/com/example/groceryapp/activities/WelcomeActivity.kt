package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

     init()


    }

    private fun init() {
//        button_register.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
//        }
//        button_login.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

        startActivity(Intent(this, LoginActivity::class.java))
    }

//    private fun init() {
//        button_register.setOnClickListener {
//            var firstName = "Syed"
//            var email = "ddd@gmail.com"
//            var password = "1234455"
//            var mobile = "12345"
//
//            var params = HashMap<String, String>()
//            params["firstName"] = firstName
//            params["email"] = email
//            params["password"] = password
//            params["mobile"] = mobile
//
//            var jsonObject = JSONObject(params as Map<* , *>) /// Covert data above into json object
//            var url = "https://grocery-second-app.herokuapp.com/api/auth/register"
//
//            var request = JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonObject,
//
//                {
//                    Toast.makeText(applicationContext, "registered", Toast.LENGTH_SHORT).show()
//
//
//                },
//                {
//
//                }
//            )
//            Volley.newRequestQueue(this).add(request)
//        }

}