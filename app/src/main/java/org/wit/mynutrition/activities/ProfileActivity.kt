package org.wit.mynutrition.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.*
import org.wit.mynutrition.models.MealModel
import org.wit.mynutrition.R
import org.wit.mynutrition.helpers.*
import org.wit.mynutrition.main.MainApp
import org.wit.mynutrition.models.Location
import org.wit.mynutrition.models.UserModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class ProfileActivity : AppCompatActivity(), AnkoLogger {

    var meal = MealModel()
    lateinit var app : MainApp

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val NUTRITION_REQUEST = 3
    val UPDATE_REQUEST = 7
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        app = application as MainApp

        toolbarProfile.title = getString(R.string.yourProfile)
        setSupportActionBar(toolbarProfile)

        updateView()


        btnMeals.setOnClickListener() {
            startActivityForResult(intentFor<MealListActivity>(), UPDATE_REQUEST)
        }

        btnChangeProfile.setOnClickListener() {
            startActivityForResult(intentFor<UpdateProfileActivity>(), UPDATE_REQUEST)
        }


    }

    private fun updateView() {
        var user = app.meals.getUser()

        imageView.setImageBitmap(readImageFromPath(this, user.image))

        fullName.setText(user.name)
        weight.setText("${getString(R.string.weightWord)}: ${user.weight} kg")
        height.setText("${getString(R.string.heightWord)}: ${user.height} cm")
        val age = getAge(user.year, user.month, user.day)

        var caloriesTC = caloriesToConsume(user.weight, user.height, age, user.male, user.activity, app.meals.findAll())
        var str : String
        if (caloriesTC < 0) {
            str = "${getString(R.string.caloriesCompleted)} ${(-caloriesTC).roundToInt()} ${getString(R.string.caloriesCompleted2)}"
        } else {
            str = caloriesTC.roundToInt().toString() + " " + getString(R.string.calories)
        }
        energyTC.setText(str)

        var proteins = proteinsToConsume(user.weight, user.activity, app.meals.findAll())
        if (proteins < 0) {
            str = "${getString(R.string.proteinsCompleted)} ${(-proteins).roundToInt()} ${getString(R.string.proteinsCompleted2)}"
        } else {
            str = proteins.roundToInt().toString() + " " + getString(R.string.protein)
        }
        proteinsTC.setText(str)

        var fat = fatToConsume(user.weight, app.meals.findAll())
        if (fat < 0) {
            str = "${getString(R.string.fatCompleted)} ${(-fat).roundToInt()} ${getString(R.string.fatCompleted2)}"
        } else {
            str = fat.roundToInt().toString() + " " + getString(R.string.fats)
        }
        fatTC.setText(str)

        var iron = ironToConsume(user, app.meals.findAll())
        if (iron < 0) {
            str = "${getString(R.string.ironCompleted)} ${(-iron).roundToInt()} ${getString(R.string.ironCompleted2)}"
        } else {
            str = iron.roundToInt().toString() + " " + getString(R.string.irons)
        }
        ironTC.setText(str)

        var calcium = calciumToConsume(user, app.meals.findAll())
        if (calcium < 0) {
            str = "${getString(R.string.calciumCompleted)} ${(-calcium).roundToInt()} ${getString(R.string.calciumCompleted2)}"
        } else {
            str = calcium.roundToInt().toString() + " " + getString(R.string.calciums)
        }
        calciumTC.setText(str)



        //energyTC.setText(getAge(Date(1999,3,9)))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item_add -> startActivityForResult<MealActivity>(UPDATE_REQUEST)
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
            UPDATE_REQUEST -> {
                updateView()
            }
        }
    }
}