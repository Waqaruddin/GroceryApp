package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints.Companion.getAddress
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.helpers.SessionManagerAddress
import com.example.groceryapp.models.MyAddress
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_submit_order.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class OrderActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var sessionManagerAddress: SessionManagerAddress
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    var mList: ArrayList<Product> = ArrayList()

    // var address:MyAddress? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)
        sessionManager = SessionManager(this)
        sessionManagerAddress = SessionManagerAddress(this)
        dbHelper = DBHelper(this)

        init()
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Review order"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    private fun init() {
        setupToolbar()

        var address = sessionManagerAddress.getAddress()
        mList = dbHelper.getProduct()
        var orderSummary = dbHelper.getOrderSummary()

        ///Preffered Address
        text_view_city.text = address.city
        text_view_pincode.text = address.pincode.toString()
        text_view_our_house_no.text = address.houseNo
        text_view_street_name.text = address.streetName

        ///Bill Breakdown
        text_view_total_amount.text = "Total Amount " + orderSummary.totalAmount.toString()
        text_view_discount.text = "Discount " + orderSummary.discount.toString()
        text_view_delivery_charges.text = "Delivery Charges " + orderSummary.deliveryCharges
        text_view_our_price.text = "Our Price " + orderSummary.ourPrice.toString()
        text_view_order_amount.text = "Order Amount " + orderSummary.totalAmount.toString()

        /// User Info
        text_view_our_mobile.text = sessionManager.getUserMobile()
        text_view_name.text = sessionManager.getUserInfo()
        text_view_email.text = sessionManager.getUserEmail()

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

        }
        return true
    }

}