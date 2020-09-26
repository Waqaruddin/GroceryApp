package com.example.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    lateinit var sessionManager:SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)
        dbHelper = DBHelper(this)

        init()
    }

    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Add Address"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    private fun init() {
        setupToolbar()

        button_save.setOnClickListener{
            radioGroup = findViewById(R.id.radio_group)
            val selectedRadioButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(selectedRadioButton)


            var pincode = edit_text_pincode.text.toString()
            var streetName = edit_text_street.text.toString()
            var city = edit_text_city.text.toString()
            var houseNo = edit_text_house.text.toString()
            var type = radioButton.text.toString()
            var userId = sessionManager.getUserId()

            if(pincode == "" || streetName == "" || city == "" || houseNo == ""){
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }else{
                var params = HashMap <String, Any>()
                params["pincode"] = pincode.toInt()
                params["streetName"] = streetName
                params["city"] = city
                params["houseNo"] = houseNo
                params["type"] = type
                params["userId"] = userId
                Log.d("abc", userId)

                var jsonObject = JSONObject(params as Map <*, *>)
                var request = JsonObjectRequest(
                    Request.Method.POST, Endpoints.saveAddress(), jsonObject,
                    {
                        Toast.makeText(applicationContext, "Address Saved", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, AddressActivity::class.java))
                    },
                    {

                    }
                )
                Volley.newRequestQueue(this).add(request)
                finish()

            }
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