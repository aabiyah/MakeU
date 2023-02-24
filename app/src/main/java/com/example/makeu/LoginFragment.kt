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


class LoginFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.login_username)
        password = view.findViewById(R.id.login_password)

        view.findViewById<Button>(R.id.button_signup).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(SignUpFragment(), false)
        }

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            validateForm()
        }
        return view
    }

    private fun validateForm() {
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
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                } else {
                    username.error = "Please Enter Valid Email ID"
                }
            }
        }
    }
}