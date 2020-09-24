package com.example.groceryapp.models

import java.io.Serializable

data class ProductResult(
    val count: Int,
    val `data`: List<Product>,
    val error: Boolean
)

data class Product(
    val __v: Int? = null,
    val _id: String? = null,
    val catId: Int? = null,
    val created: String? = null,
    val description: String? = null,
    val image: String? = null,
    val mrp: Double,
    val position: Int? = null,
    val price: Double,
    val productName: String? = null,
    var quantity: Int = 0,
    val status: Boolean? = null,
    val subId: Int? = null,
    val unit: String? = null
):Serializable {
    companion object{
        const val KEY_PRODUCT_ID = "productId"
        const val KEY_PRODUCT = "product"
    }
}

data class Totals(
    val subtotal:Double,
    val discount:Double,
    val total:Double
)