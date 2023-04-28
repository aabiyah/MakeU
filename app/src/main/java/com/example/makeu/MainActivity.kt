package com.example.makeu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// Entry point of the Android app, and it sets up the initial fragment that is displayed when the app is launched.
class MainActivity : AppCompatActivity(), FragmentNavigation {
    private lateinit var fAuth: FirebaseAuth

// In the onCreate method, the Firebase authentication object is initialized and then the current user is checked to determine whether they are logged in. If the user is logged in, the HomeFragment is added to the container, which is a FrameLayout with the ID container defined in the layout file activity_main.xml, and is added to the back stack. If the user is not logged in, the LoginFragment is added to the container.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fAuth = Firebase.auth

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


    //The navigateFrag method is an implementation of the FragmentNavigation interface, which allows fragments to communicate with the MainActivity. It is used to replace the current fragment with a new fragment and add the new fragment to the back stack if required. This method is called from the LoginFragment and HomeFragment classes to navigate between fragments.
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