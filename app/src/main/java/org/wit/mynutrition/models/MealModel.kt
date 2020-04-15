package org.wit.mynutrition.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MealModel(   var id : Long = 0,
                        var name:String="",
                        var description:String="",
                        var energyPer100:Double=0.0,
                        var fatPer100:Double=0.0,
                        var saturatedFat:Double=0.0,
                        var cholesterolPer100:Double=0.0,
                        var carbohydratePer100:Double=0.0,
                        var sugarCarbohydrate:Double=0.0,
                        var proteinsPer100:Double=0.0,
                        var fiberPer100:Double=0.0,
                        var saltPer100:Double=0.0,
                        var calciumPer100:Double=0.0,
                        var sodiumPer100:Double=0.0,
                        var ironPer100:Double=0.0,
                        var potassiumPer100:Double=0.0,
                        var vitaminAPer100:Double=0.0,
                        var vitaminB1Per100:Double=0.0,
                        var vitaminB2Per100:Double=0.0,
                        var vitaminB3Per100:Double=0.0,
                        var vitaminB5Per100:Double=0.0,
                        var vitaminB6Per100:Double=0.0,
                        var vitaminB8Per100:Double=0.0,
                        var vitaminB9Per100:Double=0.0,
                        var vitaminB12Per100:Double=0.0,
                        var vitaminCPer100:Double=0.0,
                        var image: String = "",
                        var lat : Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f,
                        var date : Date = Date()
) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable