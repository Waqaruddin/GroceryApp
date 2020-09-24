package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterCart
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    var mList:ArrayList<Product> = ArrayList()
    private var adapterCart:AdapterCart? = null
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
        mList = dbHelper.getProduct()
        adapterCart = AdapterCart(this, mList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterCart
        adapterCart?.setData(mList)

        var totals = dbHelper.getTotal(mList)
        text_view_total.text = totals.total.toString()
        text_view_discount.text = totals.discount.toString()
        text_view_subtotal.text = totals.subtotal.toString()

    }


}