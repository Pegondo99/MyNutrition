package org.wit.mynutrition.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_update_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.mynutrition.R
import org.wit.mynutrition.helpers.readImage
import org.wit.mynutrition.helpers.readImageFromPath
import org.wit.mynutrition.helpers.showImagePicker
import org.wit.mynutrition.main.MainApp
import org.wit.mynutrition.models.MealModel
import org.wit.mynutrition.models.UserModel
import android.widget.ArrayAdapter
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.jetbrains.anko.info
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class UpdateProfileActivity : AppCompatActivity(), AnkoLogger {

    var meal = MealModel()
    lateinit var app : MainApp

    var user = UserModel ()

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val NUTRITION_REQUEST = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        app = application as MainApp

        user = app.meals.getUser()

        toolbarAdd.title = getString(R.string.updateProfile)
        setSupportActionBar(toolbarAdd)

        fullName.setText(user.name)
        weight.setText(user.weight.toString())
        height.setText(user.height.toString())
        image.setImageBitmap(readImageFromPath(this, user.image))
        maleCheckbox.isChecked = user.male

        val activities = resources.getStringArray(R.array.actlvlnames)
        activitySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,  activities)

        datePicker.updateDate(user.year, user.month, user.day)





        btnAdd.setOnClickListener() {
            var name = fullName.text.toString()
            if (name == null || name == "") {
                toast (getString(R.string.noName))
            } else {
                user.name = name

                try {
                    user.height = height.text.toString().toDouble()
                    user.weight = weight.text.toString().toDouble()

                    user.male = maleCheckbox.isChecked
                    user.activity = activitySpinner.selectedItemPosition

                    /*var cal = Calendar.getInstance()
                    cal.timeInMillis = calendarView.date

                    user.year = cal.get(Calendar.YEAR)
                    user.month = cal.get(Calendar.MONTH)
                    user.day = cal.get(Calendar.DAY_OF_MONTH)*/

                    /*info("INFOO Now: ${user.toString()}")

                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val selectedDate = sdf.format(Date(calendarView.getDate()))

                    info("INFOO NEEWW ${selectedDate}")*/

                    user.year = datePicker.year
                    user.month = datePicker.month
                    user.day = datePicker.dayOfMonth




                    if (user.height == null || user.weight == null || user.height <= 0.0 || user.weight <= 0.0) {
                        toast (getString(R.string.noNegativeWeightHeight))
                    } else {
                        app.meals.setUser(user.copy())

                        setResult(AppCompatActivity.RESULT_OK)
                        finish()
                    }
                } catch (e : Exception) {
                    toast (getString(R.string.noNegativeWeightHeight))
                }
            }

        }



        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update_profile,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.item_cancel -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    user.image = data.getData().toString()
                    image.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }
}