package org.wit.mynutrition.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.mynutrition.helpers.*
import java.util.*

val JSON_FILE = "meals.json"
val USER_FILE = "user.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<MealModel>>() {}.type
val userType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MyNutritionJSONStore : MyNutritionStore, AnkoLogger {

    val context: Context
    var meals = mutableListOf<MealModel>()
    var userInfo = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
        if (exists(context, USER_FILE)) {
            deserializeUser()
        }
    }



    override fun findAll(): MutableList<MealModel> {
        return meals
    }

    override fun create(meal: MealModel) : Long {
        meal.id = generateRandomId()
        meals.add(0, meal) // To add chronologically.
        serialize()

        return meal.id;
    }


    override fun update(meal: MealModel) {
        // THE DATE IS NOT UPDATED
        var foundMeal: MealModel? = meals.find { m -> m.id == meal.id }
        if (foundMeal != null) {
            foundMeal.name = meal.name
            foundMeal.description = meal.description
            foundMeal.image = meal.image
            foundMeal.lng = meal.lng
            foundMeal.lat = meal.lat
            foundMeal.zoom = meal.zoom
            // NUTRITIONAL VALUES
            foundMeal.energyPer100 = meal.energyPer100
            foundMeal.fatPer100 = meal.fatPer100
            foundMeal.saturatedFat = meal.saturatedFat
            foundMeal.cholesterolPer100 = meal.cholesterolPer100
            foundMeal.carbohydratePer100 = meal.carbohydratePer100
            foundMeal.sugarCarbohydrate = meal.sugarCarbohydrate
            foundMeal.proteinsPer100 = meal.proteinsPer100
            foundMeal.fiberPer100 = meal.fiberPer100
            foundMeal.saltPer100 = meal.saltPer100
            foundMeal.calciumPer100 = meal.calciumPer100
            foundMeal.sodiumPer100 = meal.sodiumPer100
            foundMeal.ironPer100 = meal.ironPer100
            foundMeal.potassiumPer100 = meal.potassiumPer100
            foundMeal.vitaminAPer100 = meal.vitaminAPer100
            foundMeal.vitaminB1Per100 = meal.vitaminB1Per100
            foundMeal.vitaminB2Per100 = meal.vitaminB2Per100
            foundMeal.vitaminB3Per100 = meal.vitaminB3Per100
            foundMeal.vitaminB5Per100 = meal.vitaminB5Per100
            foundMeal.vitaminB6Per100 = meal.vitaminB6Per100
            foundMeal.vitaminB8Per100 = meal.vitaminB8Per100
            foundMeal.vitaminB9Per100 = meal.vitaminB9Per100
            foundMeal.vitaminB12Per100 = meal.vitaminB12Per100
            foundMeal.vitaminCPer100 = meal.vitaminCPer100
            // END
            serialize()
        }
    }

    override fun delete(meal: MealModel) {
        meals.remove(meal)
        serialize()
    }


    override fun getUser(): UserModel {
        return userInfo[0]
    }

    override fun setUser(user: UserModel) {
        userInfo.add(0, user.copy())
        info("INFORMATION: JSON ${userInfo[0]}")
        serializeUser()
    }




    /*override fun getUserName(): String {
        return user.name
    }

    override fun setUserName(name: String) {
        user.name = name
        serializeUser()
    }

    override fun getUserHeight(): Double {
        return user.weight
    }

    override fun setUserHeight(height: Double) {
        user.height = height
        serializeUser()
    }

    override fun getUserWeight(): Double {
        return user.weight
    }

    override fun setUserWeight(weight: Double) {
        user.weight = weight
    }

    override fun getUserImage(): String {
        return user.image
    }

    override fun setUserImage(image: String) {
        user.image = image
    }*/

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(meals, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun serializeUser() {
        val jsonUser = gsonBuilder.toJson(userInfo, userType)
        write(context, USER_FILE, jsonUser)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        meals = Gson().fromJson(jsonString, listType)
    }

    private fun deserializeUser() {
        val jsonUser = read(context, USER_FILE)
        userInfo = Gson().fromJson(jsonUser, userType)
    }
}