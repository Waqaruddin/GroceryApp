package com.example.groceryapp.models

import java.io.Serializable

data class AddressResponse(
    val count: Int,
    val `data`: List<MyAddress>,
    val error: Boolean
)

data class MyAddress(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
):Serializable {
    companion object{
        val KEY_ADDRESS = "Address"
    }
}