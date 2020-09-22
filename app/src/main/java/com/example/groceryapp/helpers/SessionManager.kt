package com.example.groceryapp.helpers

import android.content.Context
import com.example.groceryapp.models.User

class SessionManager(var mContext: Context){
    private val FILE_NAME = "Registered_users"
    private val KEY_FIRST_NAME = "firstName"
    private val USER_ID = "id"
    private val KEY_MOBILE = "mobile"
    private val KEY_EMAIL = "email"
    private val KEY_TOKEN = "token"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserInfo(user: User){
        editor.putString(KEY_FIRST_NAME, user.name)
        editor.putString(KEY_TOKEN, user.token)
        editor.commit()

    }

    fun getUserInfo():User{
        var name = sharedPreferences.getString(KEY_FIRST_NAME, null)
        var email = sharedPreferences.getString(KEY_EMAIL, null)
        return User(name, email)
    }

}