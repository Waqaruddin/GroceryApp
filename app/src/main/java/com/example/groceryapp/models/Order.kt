package com.example.groceryapp.models

data class OrderResponse(
    val count: Int,
    val `data`: List<FinalOrder>,
    val error: Boolean
)

data class FinalOrder(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<Any>,
    val shippingAddress: ShippingAddress,
    val user: User,
    val userId: String
)

data class OrderSummary(
    val _id: String? = null,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int? = null,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class ShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val mobile: String,
    val name: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)

//data class User(
//    val _id: String,
//    val email: String,
//    val mobile: String,
//    val name: String
//)