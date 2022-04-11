package com.genius.gfiilapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class FirstPageActivity : AppCompatActivity() {

    private val SplashTime: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SplashTime)


    }

//    fun run(){
//        val prefences = getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
//        val hasLoggedIn:Boolean = prefences.getBoolean("hasLoggedIn", false)
//        if(hasLoggedIn){
//            val intent = Intent(this@FirstPageActivity,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }
//        else{
//            val intent = Intent(this@FirstPageActivity,LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}