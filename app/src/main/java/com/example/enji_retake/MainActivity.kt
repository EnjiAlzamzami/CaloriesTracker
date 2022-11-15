package com.example.enji_retake

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.enji_retake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    lateinit var rvAdapter : RecyclerViewAdapter
    lateinit var Dailycalories: ArrayList<String>
    lateinit var totalCalories: ArrayList<Float>

    private lateinit var sharedPreferences: SharedPreferences


    var calories =0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        get_preferences()
        totalCalories= arrayListOf()
        Dailycalories= arrayListOf()

        rvAdapter= RecyclerViewAdapter(Dailycalories,totalCalories)
        binding.myrecyclerView.adapter=rvAdapter
        binding.myrecyclerView.layoutManager=LinearLayoutManager(this)


        binding.IntakeButton.setOnClickListener {
            Intake()
            save_preferences()
        }
        binding.BurnedButton.setOnClickListener {
            Burnedfun()
            save_preferences()
        }

        binding.undoButton.setOnClickListener{
            deleteItem()

        }



    }
    //Rotate (Life Cycle)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("Calories",calories)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        calories=savedInstanceState.getFloat("Calories",0f)
        binding.dailyCalories.setText("Daily Calories: $calories")
    }

    //App Functions
    private fun Burnedfun() {
      var calorieloses=binding.burnedTxt.text.toString()
        Dailycalories.add("Calories Burned: $calorieloses")
        calories-=calorieloses.toFloat()
        totalCalories.add(calorieloses.toFloat())
        rvAdapter.addtext(Dailycalories,totalCalories)
        binding.dailyCalories.setText("Daily Calories: $calories")
        Toast.makeText(this, "Burned Amount Added!", Toast.LENGTH_SHORT).show()

        binding.burnedTxt.text.clear()
        binding.myrecyclerView.smoothScrollToPosition(Dailycalories.size)


    }

    private fun Intake() {
       var calorietaken=binding.intakeTxt.text.toString()
        if(calories>=3000){
           // binding.dailyCalories.setTextColor(Color.RED)
            Toast.makeText(this,"Daily Calories Limit Reached",Toast.LENGTH_LONG).show()
        }else {
            Dailycalories.add("Calorie Intake: $calorietaken")
            calories += calorietaken.toFloat()
            totalCalories.add(calorietaken.toFloat())
            rvAdapter.addtext(Dailycalories,totalCalories)
            binding.dailyCalories.setText("Daily Calories: $calories")
            Toast.makeText(this, "Intake Amount Added!", Toast.LENGTH_SHORT).show()
        }
        binding.intakeTxt.text.clear()
        binding.myrecyclerView.smoothScrollToPosition(Dailycalories.size)

    }
   // delete item
    fun deleteItem(){
       if(Dailycalories.last().contains("Calories Burned:")){
           Log.d("Undo","Calories: $calories")
           calories+=totalCalories.last()
           Log.d("Undo","Calories will be: $calories")
           rvAdapter.deleteitem()
       }else {
           Log.d("Undo","Calories: $calories")
           calories-=totalCalories.last()
           Log.d("Undo","Calories will be: $calories")
           rvAdapter.deleteitem()
       }
       binding.dailyCalories.setText("Daily Calories: $calories")
        save_preferences()
       Toast.makeText(this, "Item Deleted!", Toast.LENGTH_SHORT).show()


   }

    // save data in Shared Preferences
    fun save_preferences(){
        with(sharedPreferences.edit()) {
            putFloat("calorie",  calories)
            apply()
        }

    }
    fun clear_prefrences(){
        sharedPreferences.edit().clear().commit()

    }

    // retrieves data from Shared Preferences
    fun get_preferences(){
        sharedPreferences = this.getSharedPreferences( getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        calories = sharedPreferences.getFloat("calorie", 0f)
        binding.dailyCalories.setText("Daily Calories: $calories")

    }

    //Menu Functions
    //create
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    //Selected items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.reset ->{
                reset()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //Reset Function
    private fun reset() {
        Dailycalories.clear()
        totalCalories.clear()
        calories=0f
        rvAdapter?.notifyDataSetChanged()
        clear_prefrences()
        binding.dailyCalories.setText("Daily Calories: $calories")

    }




}

