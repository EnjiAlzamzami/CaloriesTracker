package com.example.enji_retake

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enji_retake.databinding.ItemRowBinding

class RecyclerViewAdapter (private var calories : ArrayList<String>,var calorieNumber:ArrayList<Float>):
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder (var binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //return view holder
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            itemName.text = calories[position]
        }
    }

    override fun getItemCount() = calories.size

    fun addtext(calorie: ArrayList<String>,calorieNumber: ArrayList<Float>) {
        calories = calorie
        this.calorieNumber=calorieNumber
        notifyDataSetChanged()
    }

    fun deleteitem(){
       calories.removeAt(calories.size-1)
        Log.d("remove string", "deleteitem: $calories ")
       calorieNumber.removeAt(calorieNumber.size-1)
        Log.d("remove string", "deleteitem: $calorieNumber ")

        notifyDataSetChanged()
       }


}
