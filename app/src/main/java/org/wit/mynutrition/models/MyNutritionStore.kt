package org.wit.mynutrition.models

interface MyNutritionStore {
    fun findAll(): List<MealModel>
    fun create(meal: MealModel) : Long
    fun update(meal : MealModel)
    fun delete(meal: MealModel)
    fun getUser () : UserModel
    fun setUser (user : UserModel)
    /*fun getUserName () : String
    fun setUserName (name : String)
    fun getUserHeight () : Double
    fun setUserHeight (height : Double)
    fun getUserWeight () : Double
    fun setUserWeight (weight : Double)
    fun getUserImage () : String
    fun setUserImage (image : String)*/
}