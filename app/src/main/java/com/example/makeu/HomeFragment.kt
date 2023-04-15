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

class HomeFragment : Fragment() {
    private lateinit var  recyclerview: RecyclerView
    val currentUser = Firebase.auth.currentUser
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
            myRef.push().setValue(view.findViewById<EditText>(R.id.newHabitText).text.toString())
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