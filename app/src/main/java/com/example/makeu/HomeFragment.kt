package com.example.makeu

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeu.Adapters.HabitAdapter
import com.example.makeu.DataClass.Habit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var  recyclerview: RecyclerView
    val data = ArrayList<Habit>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        view.findViewById<Button>(R.id.button_logout).setOnClickListener{
            Firebase.auth.signOut()
            var navLogin = activity as FragmentNavigation
            navLogin.navigateFrag(LoginFragment(), addToStack = false)
        } //On clicking on Log Out button, signOut method is called and if signOut is successful, we move on to Login screen

        recyclerview = view.findViewById<RecyclerView>(R.id.habitRec)

        //Added dummy data, you can add more simply by copying it
        data.add(Habit("Read 50 Pages"))
        data.add(Habit("Morning"))
        data.add(Habit("Five Time Prayers"))

        setupRec()

        view.findViewById<FloatingActionButton>(R.id.button_addhabit).setOnClickListener {
            //Act like dialog-box

            view.findViewById<FrameLayout>(R.id.add_habit_layout).visibility = View.VISIBLE
            recyclerview.visibility =View.GONE
            view.findViewById<EditText>(R.id.newHabitText).text = null
        }

        view.findViewById<FrameLayout>(R.id.add_habit_layout).setOnClickListener(View.OnClickListener {
            //to hide dialog box

            view.findViewById<FrameLayout>(R.id.add_habit_layout).visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        })

        view.findViewById<Button>(R.id.newHabitButton).setOnClickListener {
            //to add new-habit

            data.add(Habit(view.findViewById<EditText>(R.id.newHabitText).text.toString()))
            setupRec()
            view.findViewById<FrameLayout>(R.id.add_habit_layout).visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
            Toast.makeText(context, "Habit Added", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun setupRec() {
        // recycler view setup
        recyclerview.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { HabitAdapter(data, it) }
        recyclerview.adapter = adapter
    }
}