package com.example.groceryapp.helpers

import android.content.Context
import com.example.groceryapp.models.User

class SessionManager(mContext: Context){
    private val FILE_NAME = "Registered_users"
    private val KEY_FIRST_NAME = "firstName"
    private val KEY_TOKEN = "token"
    private val KEY_ID = "_id"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserInfo(user: User){
        editor.putString(KEY_FIRST_NAME, user.name)
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_TOKEN, user.token)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()

    }
    fun saveUserLogin(token:String){
        editor.putString(KEY_TOKEN, token)
        editor.commit()
    }


    fun getUserId():String{
        var userId = sharedPreferences.getString(KEY_ID, null)
        return userId.toString()
    }

    fun isLoggedIn():Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }



}