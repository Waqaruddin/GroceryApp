package com.example.groceryapp.models

import java.io.Serializable

data class AddressResponse(
    val count: Int,
    val `data`: List<MyAddress>,
    val error: Boolean
)

data class MyAddress(
    val __v: Int? = null,
    val _id: String? = null,
    val city: String?= null,
    val houseNo: String? = null,
    val pincode: Int? = null,
    val streetName: String? = null,
    val type: String? = null,
    val userId: String? = null
):Serializable {
    companion object{
        const val KEY_ADDRESS = "Address"
    }
}