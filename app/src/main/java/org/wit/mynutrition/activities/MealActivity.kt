package org.wit.mynutrition.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_meal.*
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
import java.util.*
import kotlin.collections.ArrayList

class MealActivity : AppCompatActivity(), AnkoLogger {

    var meal = MealModel()
    lateinit var app : MainApp

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val NUTRITION_REQUEST = 3
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        app = application as MainApp

        val edit = intent.hasExtra("meal_edit")

        toolbarAdd.title = getString(R.string.newMeal)
        setSupportActionBar(toolbarAdd)

        if (edit) {
            meal = intent.extras.getParcelable<MealModel>("meal_edit")
            mealTitle.setText(meal.name)
            description.setText(meal.description)
            btnAdd.text = getString(R.string.button_saveMeal)
            toolbarAdd.title = getString(R.string.updateMeal)
            if (!meal.image.equals("")) chooseImage.text = getString(R.string.button_changeImage)
            mealImage.setImageBitmap(readImageFromPath(this, meal.image))

        } else {
            meal.date = Date()
        }

        info("INFORMATION: MEAL DATE: ${meal.date.toString()}")

        btnAdd.setOnClickListener() {
            meal.name = mealTitle.text.toString()
            meal.description = description.text.toString()

            if (meal.name.isNotEmpty()) {

                if (edit) {
                    app.meals.update(meal.copy())
                } else {
                    meal.id = app.meals.create(meal.copy()) // MODIFIED TO RETURN THE ID SELECTED
                }

                info("Information: Energy before: ${meal.energyPer100}" )

                startActivityForResult(intentFor<NutritionalValuesActivity>()
                    .putExtra("meal", meal.copy()), NUTRITION_REQUEST)

                info("add Button Pressed: $mealTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast (getString(R.string.no_title_toast))
            }
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        mealLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (meal.zoom != 0f) {
                location.lng = meal.lng
                location.lat = meal.lat
                location.zoom = meal.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meal,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item_cancel -> finish()
            R.id.item_delete -> {
                app.meals.delete(meal.copy())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    meal.image = data.getData().toString()
                    mealImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras.getParcelable<Location>("location")

                    meal.lng = location.lng
                    meal.lat = location.lat
                    meal.zoom = location.zoom
                }
            }
        }
    }
}