package com.example.groceryapp.app

class Endpoints {

    companion object {

        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory"
        private const val URL_PRODUCT_BY_SUB = "products/sub"
        private const val URL_PRODUCT = "product"
        private const val URL_ADDRESS = "address"

        fun getRegister():String{
            return Config.BASE_URL + URL_REGISTER
        }
        fun getLogin():String{
            return Config.BASE_URL + URL_LOGIN

        }
        fun getCategory():String {
            return Config.BASE_URL + URL_CATEGORY

        }

        fun getProduct():String{
            return Config.BASE_URL + URL_PRODUCT
        }

        fun getSubCategoryByCatId(catId: Int):String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY}/$catId"

        }

        fun getProductBySubId(subId: Int):String{
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB}/${subId}"

        }

        fun getProductByProductId(productId:String?):String{
            return "${Config.BASE_URL + URL_PRODUCT}/$productId"
        }

        fun saveAddress():String{
            return Config.BASE_URL + URL_ADDRESS
        }
        fun getAddress(userId:String):String{
            return Config.BASE_URL + URL_ADDRESS + "/" + userId
        }


    }
}