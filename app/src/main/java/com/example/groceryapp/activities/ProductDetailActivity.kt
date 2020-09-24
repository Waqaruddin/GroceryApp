package com.example.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryapp.R
import com.example.groceryapp.app.Config
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    var product: Product? = null
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product
        dbHelper = DBHelper(this)


        init()
    }

    private fun init() {
        text_view_name.text = product?.productName
        text_view_price.text = "Price: " + product?.price.toString()
        text_view_desc.text = product?.description
        Picasso.get()
            .load(Config.IMAGE_URL + product?.image)
            .into(image_view)

        button_add.setOnClickListener {
            product?.quantity = 1
            dbHelper.addProduct(product!!)

        }

    }
}


