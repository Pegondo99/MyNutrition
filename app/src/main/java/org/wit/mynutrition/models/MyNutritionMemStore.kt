package org.wit.mynutrition.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MyNutritionMemStore : MyNutritionStore, AnkoLogger {
    override fun getUser(): UserModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUser(user: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    val meals = ArrayList<MealModel>()

    override fun findAll(): List<MealModel> {
        return meals
    }

    override fun create(meal: MealModel) : Long {
        meal.id= getId()
        meals.add(meal)
        logAll()
        return meal.id;
    }

    override fun update(meal: MealModel) {
        var foundMeal: MealModel? = meals.find { p -> p.id == meal.id }
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
            logAll()
        }
    }

    override fun delete(meal: MealModel) {
        meals.remove(meal)
    }

    fun logAll() {
        meals.forEach { info("${it}") }
    }
}