package com.example.splitease.ui.Startup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.splitease.MainActivity
import com.example.splitease.R
import com.example.splitease.ui.Login.Login
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    val auth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //remove night mode

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
        Handler().postDelayed({
            if(auth.currentUser == null) { //if user not logged in
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
                finish()

            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 10)
    }
}