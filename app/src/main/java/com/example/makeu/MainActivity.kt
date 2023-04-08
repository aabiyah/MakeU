package com.example.makeu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity(override var database: FirebaseDatabase) : AppCompatActivity(), FragmentNavigation {
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = Firebase.auth
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // Get Firebase Realtime Database instance
        database = FirebaseDatabase.getInstance()
        // Store model data in Firebase Realtime Database
        val modelData = "value1"
        val modelDataRef = database.reference.child("modelData")
        modelDataRef.setValue(modelData)

        val currentUser = fAuth.currentUser
        if(currentUser != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, HomeFragment()).addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }

    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if(addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}