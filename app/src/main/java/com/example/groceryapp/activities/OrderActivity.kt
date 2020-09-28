package com.example.groceryapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.app.Endpoints.Companion.getAddress
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.helpers.SessionManagerAddress
import com.example.groceryapp.models.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_submit_order.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import org.json.JSONObject
import java.time.LocalDateTime

class OrderActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var sessionManagerAddress: SessionManagerAddress
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    var mList: ArrayList<Product> = ArrayList()

    var address: MyAddress? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)
        sessionManager = SessionManager(this)
        sessionManagerAddress = SessionManagerAddress(this)
        dbHelper = DBHelper(this)

        init()
    }

    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "Review order"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    @RequiresApi(Build.VERSION_CODES.O)
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

        button_submit_order.setOnClickListener {
            postOrder()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPaymentResponse(): PaymentResponse {
        var address = sessionManagerAddress.getAddress()

        val products = dbHelper.getProduct()
        val date = LocalDateTime.now()
        val orderStatus = "Confirmed"
        var orderSummary = dbHelper.getOrderSummary()
        val payment = Payment("Cash", "Pending")
        val shippingAddress = ShippingAddress(
            address.city!!,
            address.houseNo!!,
            address.pincode!!,
            address.streetName!!
        )
        val user = PaymentUser(
            sessionManager.getUserId(),
            sessionManager.getUserEmail(),
            sessionManager.getUserMobile(),
            sessionManager.getUserInfo()
        )
        val userId = sessionManager.getUserId()
        return PaymentResponse(
            date.toString(),
            orderStatus,
            orderSummary,
            payment,
            products,
            shippingAddress,
            user,
            userId
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun postOrder() {
        val gson = Gson()
        val postOrder = gson.toJson(createPaymentResponse())
        var jsonObject = JSONObject(postOrder)
        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.postOrder(),
            jsonObject,
            {
                Toast.makeText(this, "Posted Order", Toast.LENGTH_SHORT).show()
                dbHelper.emptyCart()
                startActivity(Intent(this, MainActivity::class.java))

            },
            {
                Toast.makeText(this, "Error posting order", Toast.LENGTH_SHORT).show()
            }
        )
        Volley.newRequestQueue(this).add(request)

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


    private fun updateCartCount() {
        var count = dbHelper.getCartTotalQuantity()
        //var count = 10
        if (count == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

        }
        return true
    }
}

