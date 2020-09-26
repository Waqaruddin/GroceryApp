package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterFragment
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.SubCategory
import com.example.groceryapp.models.SubCategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import java.lang.reflect.Method

class SubCategoryActivity : AppCompatActivity() {
    var category:Category? = null
    var mList:ArrayList<SubCategory> = ArrayList()
    var textViewCartCount: TextView? = null
    lateinit var dbHelper: DBHelper
    lateinit var adapterFragment: AdapterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        dbHelper = DBHelper(this)

        category = intent.getSerializableExtra(Category.KEY_CATEGORY) as Category

        init()
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = category!!.catName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        setupToolbar()
        getData(category!!.catId)
        adapterFragment = AdapterFragment(supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        var item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        view.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        updateCartCount()

        return true
    }


    private fun updateCartCount(){
        var count = dbHelper.getCartTotalQuantity()
        //var count = 10
        if(count == 0 ){
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
            R.id.action_cart -> Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
            R.id.action_profile -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
            R.id.action_setting -> Toast.makeText(applicationContext, "Setting", Toast.LENGTH_SHORT).show()

        }
        return true
    }


    private fun getData(catId:Int){
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catId),
            {
                var gson = Gson()
                var subCategoryResult = gson.fromJson(it, SubCategoryResult::class.java)
                mList.addAll(subCategoryResult.data)

                for(i in 0 until mList.size){
                    adapterFragment.addFragment(mList[i])
                }

                view_pager.adapter = adapterFragment
                tab_layout.setupWithViewPager(view_pager)


            },
            {

            }

        )
        Volley.newRequestQueue(this).add(request)
    }


}