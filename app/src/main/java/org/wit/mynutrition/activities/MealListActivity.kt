package org.wit.mynutrition.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_meal_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.mynutrition.R
import org.wit.mynutrition.main.MainApp
import org.wit.mynutrition.models.MealModel


class MealListActivity : AppCompatActivity(), MyNutritionListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadMeals()

        toolbarMain.title = getString(R.string.myMeals)
        setSupportActionBar(toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MealActivity>(0)
            R.id.item_cancel -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMealClick(meal: MealModel) {
        startActivityForResult(intentFor<MealActivity>().putExtra("meal_edit", meal), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadMeals()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadMeals() {
        showMeals(app.meals.findAll())
    }

    fun showMeals (meals: List<MealModel>) {
        recyclerView.adapter = MyNutritionAdapter(meals, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}

