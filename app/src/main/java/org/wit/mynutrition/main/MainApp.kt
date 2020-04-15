package org.wit.mynutrition.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.mynutrition.models.MealModel
import org.wit.mynutrition.models.MyNutritionJSONStore
import org.wit.mynutrition.models.MyNutritionMemStore
import org.wit.mynutrition.models.MyNutritionStore

class MainApp : Application(), AnkoLogger {

    lateinit var meals : MyNutritionStore

    override fun onCreate() {
        super.onCreate()
        meals = MyNutritionJSONStore(applicationContext)
        info ("MyNutrition started")

    }
}