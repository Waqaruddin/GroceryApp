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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class CartActivity : AppCompatActivity() {
    var mList:ArrayList<Product> = ArrayList()
    private var adapterCart:AdapterCart? = null
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        dbHelper = DBHelper(this)
        init()
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Shopping Cart"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        setupToolbar()

        mList = dbHelper.getProduct()
        adapterCart = AdapterCart(this, mList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterCart
        adapterCart?.setData(mList)

        var orderSummary = dbHelper.getOrderSummary()

        text_view_total.text = "$" + orderSummary.ourPrice.toString()
        text_view_discount.text = "$" + orderSummary.discount.toString()
        text_view_subtotal.text = "$" +  orderSummary.totalAmount.toString()

        button_checkout.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        var item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count

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
//            R.id.action_cart -> Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
//            R.id.action_profile -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
//            R.id.action_setting -> Toast.makeText(applicationContext, "Setting", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        mList = dbHelper.getProduct()
        adapterCart?.setData(mList)
    }



}