package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterAddress
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.AddressResponse
import com.example.groceryapp.models.MyAddress
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.recycler_view
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.reflect.Method

class AddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    var mList:ArrayList<MyAddress> = ArrayList()
    private var adapterAddress:AdapterAddress? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        button_add.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
            finish()
        }

        getData()
        adapterAddress = AdapterAddress(this, mList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterAddress

    }

    private fun getData() {
        var userId = "5f64e8f1af18dc0017608565"
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getAddress(userId),
            {
                val gson = Gson()
                var addressRespone = gson.fromJson(it.toString(),AddressResponse::class.java)
                mList.addAll(addressRespone.data)
                adapterAddress!!.setData(mList)
            },
            {

            }


        )
        Volley.newRequestQueue(this).add(request)
    }


}