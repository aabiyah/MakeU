package com.example.makeu

import androidx.fragment.app.Fragment

//The FragmentNavigation interface is used to allow communication between fragments and the MainActivity in an Android app. It defines a single method, navigateFrag(), which takes in a Fragment object and a Boolean value to indicate whether the fragment should be added to the back stack.
//
//Fragments use this interface to request navigation between different fragments, allowing the MainActivity to handle fragment transactions and manage the back stack. For example, when the user logs in successfully, the LoginFragment can use the navigateFrag() method to request navigation to the HomeFragment.
//
//By defining this interface, fragments can communicate with the MainActivity without creating a direct dependency, allowing for greater flexibility in the app's architecture.

interface FragmentNavigation {
    fun navigateFrag(fragment: Fragment, addToStack: Boolean)
}