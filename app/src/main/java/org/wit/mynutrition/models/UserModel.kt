package org.wit.mynutrition.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class UserModel(var name : String = "",
                     var height : Double = 0.1,
                     var weight : Double = 0.1,
                     var image : String = "",
                     var year : Int = 2000,
                     var month : Int = 0,
                     var day : Int = 1,
                     var activity : Int = 0,
                     var male : Boolean = true

                    ) : Parcelable
