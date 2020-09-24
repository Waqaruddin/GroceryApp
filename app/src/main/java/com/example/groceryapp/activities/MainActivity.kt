package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCategory
import com.example.groceryapp.app.Config
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.CategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var sessionManager:SessionManager
    var mList:ArrayList<Category> = ArrayList()
    lateinit var adapterCategory:AdapterCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)

        init()
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Grocery App"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        setupToolbar()
        getData()
        adapterCategory = AdapterCategory(this)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.adapter = adapterCategory
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when (item.itemId){
            R.id.action_cart -> startActivity(Intent(this, CartActivity::class.java))
             R.id.action_profile -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
             R.id.action_setting -> Toast.makeText(applicationContext, "Setting", Toast.LENGTH_SHORT).show()
             R.id.action_logout -> {sessionManager.logout()
                 startActivity(Intent(applicationContext, LoginActivity::class.java))}


         }
        return true
    }

    private fun getData() {
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            {
                var gson = Gson()
                var categoryResult = gson.fromJson(it, CategoryResult::class.java)
               // Log.d("abc", categoryResult.data[0].catName)
                mList.addAll(categoryResult.data)
                adapterCategory.setData(mList)
                progress_bar.visibility = View.GONE

            },
            {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()

            }

        )
        Volley.newRequestQueue(this).add(request)
    }
}