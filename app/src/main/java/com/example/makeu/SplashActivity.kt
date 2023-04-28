package com.example.makeu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        val i = Intent(this@SplashActivity, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(i)
            finish()
        }, 2000)
    }
}

//This SplashActivity code creates an activity that acts as a splash screen for the app.
//
//In the onCreate method, the activity layout is set and the support action bar is hidden. Then an Intent is created to launch the main activity of the app, which is MainActivity.
//
//Next, a Handler is used to delay the start of the main activity by 2 seconds (2000 milliseconds). After the delay, the startActivity() method is called to start the MainActivity, and the finish() method is called to close the SplashActivity.
//
//This code ensures that the SplashActivity is displayed when the app is launched and stays visible for a short period of time before automatically transitioning to the main activity. It provides a simple and visually appealing way to indicate that the app is loading.