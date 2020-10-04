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
import com.example.groceryapp.app.Config
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class ProductDetailActivity : AppCompatActivity(), View.OnClickListener {
    var product: Product? = null
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product
        dbHelper = DBHelper(this)


        init()
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = product!!.productName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateUI(){
        var quantity = dbHelper.getProductQuantityFromCart(product!!)
        if(quantity > 0){
            button_add.visibility = View.INVISIBLE
            product_add_layout.visibility = View.VISIBLE
            product_count.text = quantity.toString()
        }else{
            button_add.visibility = View.VISIBLE
            product_add_layout.visibility = View.INVISIBLE

        }
    }

    private fun init() {
        setupToolbar()
        updateUI()
        setData(product!!)
        button_add.setOnClickListener(this)
        product_add_sign.setOnClickListener(this)
        product_sub_sign.setOnClickListener(this)

    }

    private fun setData(product: Product){

        text_view_name.text = product?.productName
        text_view_price.text = "Price: " + product?.price.toString()
        text_view_desc.text = product?.description
        Picasso.get()
            .load(Config.IMAGE_URL + product?.image)
            .into(image_view)


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

    override fun onClick(view: View) {
        when(view){
            button_add -> {
                product?.quantity = 1
                dbHelper.addProduct(product!!)
                updateUI()
            }
            product_add_sign -> {
                product?.quantity = product!!.quantity +1
                dbHelper.updateProductQuantity(product!!)
                updateUI()

            }
            product_sub_sign -> {
                product?.quantity = product!!.quantity - 1
                if(product!!.quantity > 0 ){
                    dbHelper.updateProductQuantity(product!!)
                }else{
                    dbHelper.deleteProduct(product!!._id!!)
                }
                updateUI()


            }
        }
    }

}


