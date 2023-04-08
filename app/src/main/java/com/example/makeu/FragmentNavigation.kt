package com.example.makeu

import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase

interface FragmentNavigation {
    abstract val database: FirebaseDatabase

    fun navigateFrag(fragment: Fragment, addToStack: Boolean)
}