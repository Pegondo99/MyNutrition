package org.wit.mynutrition.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.nutritional_values.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.mynutrition.models.MealModel
import org.wit.mynutrition.R
import org.wit.mynutrition.helpers.readImage
import org.wit.mynutrition.helpers.readImageFromPath
import org.wit.mynutrition.helpers.showImagePicker
import org.wit.mynutrition.main.MainApp
import org.wit.mynutrition.models.Location
import java.lang.Exception

class NutritionalValuesActivity : AppCompatActivity(), AnkoLogger {

    lateinit var meal : MealModel
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nutritional_values)
        app = application as MainApp

        toolbarAdd.title = getString(R.string.toolbarNutrition)
        setSupportActionBar(toolbarAdd)

        meal = intent.extras.getParcelable<MealModel>("meal")

        info("Information: energy of ${meal.name} recovered: ${meal.energyPer100}")

        chargeTexts()

        btnAdd.text = getString(R.string.button_saveMeal)


        btnAdd.setOnClickListener() {
            info("Information: adding/updating ${meal.name}")
            try {
                chargeMeal()
            }catch (e : Exception) {
            }

            app.meals.update(meal.copy())

            setResult(AppCompatActivity.RESULT_OK)
            finish()

        }
    }

    private fun chargeMeal() {
        meal.energyPer100 = energy.text.toString().toDouble()
        meal.fatPer100 = fat.text.toString().toDouble()
        meal.saturatedFat = saturatedFat.text.toString().toDouble()
        meal.cholesterolPer100 = cholesterol.text.toString().toDouble()
        meal.carbohydratePer100 = carbohydrate.text.toString().toDouble()
        meal.sugarCarbohydrate = sugarCarbohydrate.text.toString().toDouble()
        meal.proteinsPer100 = proteins.text.toString().toDouble()
        meal.fiberPer100 = fiber.text.toString().toDouble()
        meal.saltPer100 = salt.text.toString().toDouble()
        meal.fatPer100 = fat.text.toString().toDouble()
        meal.calciumPer100 = calcium.text.toString().toDouble()
        meal.sodiumPer100 = sodium.text.toString().toDouble()
        meal.ironPer100 = iron.text.toString().toDouble()
        meal.potassiumPer100 = potassium.text.toString().toDouble()
        meal.vitaminAPer100 = vitA.text.toString().toDouble()
        meal.vitaminB1Per100 = vitB1.text.toString().toDouble()
        meal.vitaminB2Per100 = vitB2.text.toString().toDouble()
        meal.vitaminB3Per100 = vitB3.text.toString().toDouble()
        meal.vitaminB5Per100 = vitB5.text.toString().toDouble()
        meal.vitaminB6Per100 = vitB6.text.toString().toDouble()
        meal.vitaminB8Per100 = vitB8.text.toString().toDouble()
        meal.vitaminB9Per100 = vitB9.text.toString().toDouble()
        meal.vitaminB12Per100 = vitB12.text.toString().toDouble()
        meal.vitaminCPer100 = vitC.text.toString().toDouble()
    }

    private fun chargeTexts() {
        energy.setText(meal.energyPer100.toString())
        fat.setText(meal.fatPer100.toString())
        saturatedFat.setText(meal.saturatedFat.toString())
        cholesterol.setText(meal.cholesterolPer100.toString())
        carbohydrate.setText(meal.carbohydratePer100.toString())
        sugarCarbohydrate.setText(meal.sugarCarbohydrate.toString())
        proteins.setText(meal.proteinsPer100.toString())
        fiber.setText(meal.fiberPer100.toString())
        salt.setText(meal.saltPer100.toString())
        calcium.setText(meal.calciumPer100.toString())
        sodium.setText(meal.sodiumPer100.toString())
        iron.setText(meal.ironPer100.toString())
        potassium.setText(meal.potassiumPer100.toString())
        energy.setText(meal.energyPer100.toString())
        vitA.setText(meal.vitaminAPer100.toString())
        vitB1.setText(meal.vitaminB1Per100.toString())
        vitB2.setText(meal.vitaminB2Per100.toString())
        vitB3.setText(meal.vitaminB3Per100.toString())
        vitB5.setText(meal.vitaminB5Per100.toString())
        vitB6.setText(meal.vitaminB6Per100.toString())
        vitB8.setText(meal.vitaminB8Per100.toString())
        vitB9.setText(meal.vitaminB9Per100.toString())
        vitB12.setText(meal.vitaminB12Per100.toString())
        vitC.setText(meal.vitaminCPer100.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meal,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.meals.delete(meal.copy())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}