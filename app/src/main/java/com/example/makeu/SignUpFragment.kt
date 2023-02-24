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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class SignUpFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        username = view.findViewById(R.id.reg_username)
        password = view.findViewById(R.id.reg_password)
        confirmPassword = view.findViewById(R.id.reg_confirm_password)

        view.findViewById<Button>(R.id.button_login_reg).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(LoginFragment(), false)
        }
        view.findViewById<Button>(R.id.button_signup_reg).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }

    private fun validateEmptyForm() {
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username")
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password")
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim()) -> {
                confirmPassword.setError("Passwords Don't Match!")
            }
            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    confirmPassword.text.toString().isNotEmpty() -> {
                        if(username.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                            if(password.text.toString().length>=5) {
                                if(password.text.toString() == confirmPassword.text.toString()) {
                                    Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                                } else {
                                    confirmPassword.setError("Passwords Don't Match!")
                                }
                            } else {
                                password.setError("Please Enter At Least 5 Characters")
                            }
                        } else {
                            username.setError("Please Enter Valid Email ID")
                        }
                    }
        }
    }
}