package com.example.groceryapp.helpers

import android.content.Context
import com.example.groceryapp.models.MyAddress

class SessionManagerAddress(var mContext: Context){
    private val FILE_NAME = "DEFAULT_ADDRESS"
    private val KEY_PIN = "pincode"
    private val KEY_STREET = "streetName"
    private val KEY_HOUSE = "houseNo"
    private val KEY_TYPE = "type"
    private val KEY_CITY = "city"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()



    fun saveAddress(address: MyAddress){
        editor.putString(KEY_PIN, address.pincode.toString())
        editor.putString(KEY_STREET, address.streetName)
        editor.putString(KEY_HOUSE, address.houseNo)
        editor.putString(KEY_TYPE, address.type)
        editor.putString(KEY_CITY, address.city)
        editor.commit()
    }

    fun getAddress():MyAddress{
        var pincode = sharedPreferences.getString(KEY_PIN, null)
        var street = sharedPreferences.getString(KEY_STREET, null)
        var house = sharedPreferences.getString(KEY_HOUSE, null)
        var type = sharedPreferences.getString(KEY_TYPE, null)
        var city = sharedPreferences.getString(KEY_CITY, null)

        return MyAddress(pincode = pincode?.toInt(), streetName = street, houseNo = house, type = type, city = city)

    }
}