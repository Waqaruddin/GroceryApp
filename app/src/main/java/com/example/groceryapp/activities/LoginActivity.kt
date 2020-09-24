package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.LoginResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.net.Inet4Address

class LoginActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        button_login.setOnClickListener{
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()

            var params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password

            var jsonObject = JSONObject(params as Map<*, *>)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                jsonObject,
                {
                   // Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                    val gson = Gson()
                    var loginResponse = gson.fromJson(it.toString(), LoginResponse::class.java)
                    sessionManager.saveUserInfo(loginResponse.user)
                    sessionManager.saveUserLogin(loginResponse.token!!)

                    startActivity(Intent(this, MainActivity::class.java))
                },
                {
                    Toast.makeText(applicationContext, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                }

            )
            Volley.newRequestQueue(this).add(request)

        }
    }


}