package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints.Companion.getAddress
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import com.example.groceryapp.helpers.SessionManagerAddress
import com.example.groceryapp.models.MyAddress
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_submit_order.*

class OrderActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var sessionManagerAddress: SessionManagerAddress
    lateinit var dbHelper: DBHelper

    var mList:ArrayList<Product> = ArrayList()
   // var address:MyAddress? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_order)
        sessionManager = SessionManager(this)
       sessionManagerAddress = SessionManagerAddress(this)
         dbHelper = DBHelper(this)

        init()
    }

    private fun init() {
        var address = sessionManagerAddress.getAddress()
        mList = dbHelper.getProduct()
        var totals = dbHelper.getTotal(mList)

        ///Preffered Address
        text_view_city.text = address.city
        text_view_pincode.text = address.pincode.toString()
        text_view_our_house_no.text = address.houseNo
        text_view_street_name.text = address.streetName

        ///Bill Break Down
        text_view_total_amount.text = totals.subtotal.toString()
        text_view_discount.text = totals.discount.toString()
        text_view_order_amount.text = totals.total.toString()






        button_submit_order.setOnClickListener {
            var userId = sessionManager.getUserId()
        }
    }

    private fun getAddressInfo() {
//        text_view_city.text = address?.city
//        text_view_our_house_no.text = address?.houseNo
//        text_view_pincode.text = address?.pincode.toString()
    }
}