package com.example.makeu.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.makeu.DataClass.Habit
import com.example.makeu.R
//Adapter classes allow default implementation of listeners
class HabitAdapter(private val list:List<Habit>, context: Context): RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = list[position]
        holder.habitText.text = itemsViewModel.habit
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: CheckBox = itemView.findViewById(R.id.habitCheckBox)
        val habitText: TextView = itemView.findViewById(R.id.habitText);
    }
}