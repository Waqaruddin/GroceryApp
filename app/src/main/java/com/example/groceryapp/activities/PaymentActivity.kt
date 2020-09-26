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
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.text_view_discount
import kotlinx.android.synthetic.main.activity_cart.text_view_subtotal
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class PaymentActivity : AppCompatActivity() {
    var mList:ArrayList<Product> = ArrayList()
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        dbHelper = DBHelper(this)
        init()
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Review payment"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        setupToolbar()

        mList = dbHelper.getProduct()
        var totals = dbHelper.getTotal(mList)
        text_view_subtotal.text = "Subtotal " + totals.subtotal.toString()
        text_view_discount.text = "Discount " + totals.discount.toString()
        text_view_order_amount.text = "Total " + totals.total.toString()
        text_view_total_amount.text = totals.total.toString()

        button_pay_online.setOnClickListener {
            Toast.makeText(this, "Accepting Cash Only", Toast.LENGTH_SHORT).show()
        }

        button_pay_delivery.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))

        }
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