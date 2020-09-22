package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterFragment
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Category
import com.example.groceryapp.models.SubCategory
import com.example.groceryapp.models.SubCategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import java.lang.reflect.Method

class SubCategoryActivity : AppCompatActivity() {
    var category:Category? = null
    var mList:ArrayList<SubCategory> = ArrayList()
    lateinit var adapterFragment: AdapterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
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