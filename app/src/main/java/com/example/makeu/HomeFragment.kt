package com.example.makeu

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


//This code is a Kotlin class called "HomeFragment" that extends the AndroidX Fragment class. The purpose of this class is to define the behavior of a fragment in an Android app that displays a list of habits and allows the user to add new habits.
//
//When the fragment is created, it initializes a RecyclerView to display the list of habits, sets up a connection to the Firebase Realtime Database to read and write habit data, and sets up click listeners for buttons to allow the user to log out, add a new habit.

class HomeFragment : Fragment() {
    private lateinit var  recyclerview: RecyclerView
    val currentUser = Firebase.auth.currentUser
    val data = ArrayList<Habit>()


//    This method is used to inflate a layout file and initialize the views that are contained within it.
//
//    In the HomeFragment class, the onCreateView method inflates a layout file called fragment_home.xml and sets up click listeners for buttons and the RecyclerView. It also initializes the Firebase Realtime Database reference and sets up an event listener to listen for changes in the "habit" node and update the RecyclerView when the data changes.
//
//    Once the view hierarchy is created and initialized, the onCreateView method returns the top-level view of the layout file to be displayed in the fragment.
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

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("habit")

        //myRef.setValue("Hello, World!")
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (habitSnap in snapshot.children) {
                    val value = habitSnap.getValue<String>()
                    data.add(Habit(value.toString()))
                    Log.d("Value", "Value is: " + value)
                }
                setupRec()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Value", "Failed to read value.", error.toException())
            }

        })


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
            data.clear()
            myRef.push().setValue(view.findViewById<EditText>(R.id.newHabitText).text.toString())
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