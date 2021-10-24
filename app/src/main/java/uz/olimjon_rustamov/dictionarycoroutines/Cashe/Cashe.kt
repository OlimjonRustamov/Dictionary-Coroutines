package uz.olimjon_rustamov.dictionarycoroutines.Cashe

import android.content.Context
import android.content.SharedPreferences

class Cashe private constructor(context: Context) {

    init {
        preferences = context.getSharedPreferences("details", Context.MODE_PRIVATE)
    }

    fun setStatus() {
        editor = preferences.edit()
        editor!!.putString("status","entered")
        editor!!.apply()
    }

    fun getStatus(): String {
        return preferences.getString("status", "first")!!
    }
    fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {
        var instance: Cashe? = null
            private set
        private lateinit var preferences: SharedPreferences
        private var editor: SharedPreferences.Editor?=null

        fun init(context: Context) {
            if (instance == null) {
                instance = Cashe(context)
            }
        }
    }

}