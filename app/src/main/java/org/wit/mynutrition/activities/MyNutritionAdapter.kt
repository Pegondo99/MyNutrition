package org.wit.mynutrition.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_meal.view.*
import kotlinx.android.synthetic.main.card_meal.view.*
import kotlinx.android.synthetic.main.card_meal.view.description
import kotlinx.android.synthetic.main.card_meal.view.mealImage
import kotlinx.android.synthetic.main.card_meal.view.mealTitle
import org.wit.mynutrition.R
import org.wit.mynutrition.helpers.readImageFromPath
import org.wit.mynutrition.models.MealModel
import java.text.SimpleDateFormat


interface MyNutritionListener {
    fun onMealClick(meal: MealModel)
}

class MyNutritionAdapter constructor(private var meals: List<MealModel>, private val listener : MyNutritionListener) : RecyclerView.Adapter<MyNutritionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_meal, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val meal = meals[holder.adapterPosition]
        holder.bind(meal, listener)
    }

    override fun getItemCount(): Int = meals.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(meal: MealModel, listener : MyNutritionListener) {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            val d = meal.date

            //val months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            itemView.mealTitle.text = meal.name
            itemView.description.text = meal.description

            itemView.date.text = "Eaten on ${dateFormat.format(d)}"
            itemView.mealImage.setImageBitmap(readImageFromPath(itemView.context, meal.image))
            itemView.setOnClickListener { listener.onMealClick(meal) }
        }
    }
}