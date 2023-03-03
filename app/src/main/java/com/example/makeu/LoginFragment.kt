package com.example.makeu

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class LoginFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.login_username)
        password = view.findViewById(R.id.login_password)
        fAuth = Firebase.auth

        view.findViewById<Button>(R.id.button_signup).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(SignUpFragment(), false)
        }

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            validateForm()
        }
        return view
    }

    private fun firebaseSignIn() {
        val loginbutton = view?.findViewById<Button>(R.id.button_login)
        loginbutton?.isEnabled = false
        loginbutton?.alpha = 0.5f //Hide button if Login already in progress
        fAuth.signInWithEmailAndPassword(username.text.toString(), password.text.toString()).addOnCompleteListener {
            task ->
            if(task.isSuccessful) {
                var navHome = activity as FragmentNavigation
                navHome.navigateFrag(HomeFragment(), addToStack = true)
            } else {
                loginbutton?.isEnabled = true
                loginbutton?.alpha = 1.0f
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

     public fun validateForm() {
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.error = "Please Enter Username"
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.error = "Please Enter Password"
            }

            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty()  -> {
                if(username.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    firebaseSignIn()
                } else {
                    username.error = "Please Enter Valid Email ID"
                }
            }
        }
    }


}