package org.wit.mynutrition.helpers

import org.wit.mynutrition.models.MealModel
import org.wit.mynutrition.models.UserModel
import java.util.*


fun caloriesToConsume(weight : Double, height : Double, age : Int, male : Boolean, activity : Int, meals : List<MealModel>) : Double {
    var bmr : Double
    if (male) {
        bmr = 66 + (13.7*weight) + (5*height) - (6.75*age)
    } else {
        bmr = 655 + (9.6*weight) + (1.8*height) - (4.7*age)
    }
    var tc : Double
    tc = when (activity) {
        0 -> {1.2*bmr}
        1 -> {1.375*bmr}
        2 -> {1.55*bmr}
        3 -> {1.72*bmr}
        else -> {1.9*bmr}
    }

    return tc - consummedCal(meals)
}

fun consummedCal(meals : List<MealModel>): Double {
    var todays : List<MealModel> = meals.filter {m -> sameDay(m.date)}
    var res = 0.0
    for (m in todays) {
        res += m.energyPer100
    }
    return res
}

fun sameDay(date: Date): Boolean {
    val today = Date()
    return today.year == date.year && today.month == date.month && today.day == date.day
}

fun getAge(year : Int, month : Int, day : Int) : Int {

    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()

    dob.set(year, month, day)

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    val ageInt = age

    return ageInt

}

fun proteinsToConsume (weight : Double, activity : Int, meals : List<MealModel> ) : Double{
    var res = 0.0
    when (activity) {
        0 -> res = 0.8*weight
        1 -> res = 1.3*weight
        2 -> res = 1.7*weight
        3 -> res = 2.0*weight
        else -> res = 2.5*weight
    }

    return res - consummedProt(meals)
}

fun consummedProt(meals : List<MealModel>): Double {
    var todays : List<MealModel> = meals.filter {m -> sameDay(m.date)}
    var res = 0.0
    for (m in todays) {
        res += m.proteinsPer100
    }
    return res
}

fun fatToConsume (weight: Double, meals : List<MealModel>) : Double{
    return weight - consummedFat(meals)
}

fun consummedFat(meals: List<MealModel>): Double {
    var todays : List<MealModel> = meals.filter {m -> sameDay(m.date)}
    var res = 0.0
    for (m in todays) {
        res += m.fatPer100
    }
    return res
}

fun ironToConsume(user : UserModel, meals : List<MealModel>) : Double {
    var age = getAge(user.year, user.month, user.day)
    var res = 0.0

    if (age < 1) {
        res = 11.0
    } else if (age < 3) {
        res = 7.0
    } else if (age < 8) {
        res = 10.0
    } else if (age < 13) {
        res = 8.0
    } else if (age < 18) {
        if (user.male) {
            res= 11.0
        } else {
            res= 15.0
        }

    } else if (age < 50) {
        if (user.male)  {
            res= 8.0
        } else {
            res =18.0
        }
    } else {
        res = 8.0
    }
    return res - consummedIron(meals)
}

fun consummedIron(meals: List<MealModel>): Double {
    var todays : List<MealModel> = meals.filter {m -> sameDay(m.date)}
    var res = 0.0
    for (m in todays) {
        res += m.ironPer100
    }
    return res
}

fun calciumToConsume(user : UserModel, meals : List<MealModel>) : Double {
    var res = 0.0
    val age = getAge(user.year, user.month, user.day)
    if (user.male) {
        if (age < 70) {
            res = 1000.0
        } else {
            res = 1200.0
        }
    } else {
        if (age < 50) {
            res = 1000.0
        } else {
            res = 1200.0
        }
    }
    return res - consummedCalc(meals)
}

fun consummedCalc(meals: List<MealModel>): Double {
    var todays : List<MealModel> = meals.filter {m -> sameDay(m.date)}
    var res = 0.0
    for (m in todays) {
        res += m.calciumPer100
    }
    return res
}

