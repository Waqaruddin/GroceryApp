package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.text_view_discount
import kotlinx.android.synthetic.main.activity_cart.text_view_subtotal
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    var mList:ArrayList<Product> = ArrayList()
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
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
            Toast.makeText(this, "Cash on Delivery", Toast.LENGTH_SHORT).show()

        }
    }
}