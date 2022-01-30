package com.pandey.shri.utils

import android.annotation.TargetApi
import android.os.Build
import android.util.Log
import com.pandey.shri.R
import com.pandey.shri.data.local.db.Converters
import com.pandey.shri.data.model.Entry
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.math.absoluteValue

class Utils {
    companion object {
        private const val TAG = "Utils"
        fun getMonthName(intMonth: Int): String {

            return when (intMonth) {
                0 -> "Jan"
                1 -> "Feb"
                2 -> "Mar"
                3 -> "Apr"
                4 -> "May"
                5 -> "Jun"
                6 -> "Jul"
                7 -> "Aug"
                8 -> "Sep"
                9 -> "Oct"
                10 -> "Nov"
                else -> {
                    "Dec"
                }
            }
        }

        fun setCategoryImage(categoryName: String): Int {

            return when (categoryName) {
                "Recharge" -> R.drawable.ic_cell_phone
                "Vegetable" -> R.drawable.ic_vegetables
                "Cloth" -> R.drawable.ic_cloths
                "Electric" -> R.drawable.ic_electrical_appliances
                "Fare" -> R.drawable.ic_cab
                "Tuition Fee" -> R.drawable.ic_open_book
                "Fast food" -> R.drawable.ic_fast_food

                else -> {
                    R.drawable.ic_rupee
                }
            }
        }


        // calculate  total expense

        fun grandTotal(items: List<Entry>): Int {
            var totalPrice = 0
            for (i in items.indices) {
                totalPrice += items[i].itemPrice
            }
            return totalPrice
        }

        fun calculatePercentage(grandTotal: Int, categoryTotal: Int): Float {

            val percentage = (categoryTotal / grandTotal) * 100
            return percentage.toFloat()

        }


        fun getFromDate(): OffsetDateTime {

            lateinit var stringToTest: String
            val date = OffsetDateTime.now()
            val currentMonth = date.month.value
            val currentYear = date.year.absoluteValue
            if (currentMonth < 10) {
                stringToTest = "$currentYear-0$currentMonth-01T01:00:00+00:00"

            } else {
                stringToTest = "$currentYear-$currentMonth-01T01:00:00+00:00"

            }

            val startDate = Converters.toOffsetDateTime(stringToTest)

            return startDate
        }

        fun getToDate(): OffsetDateTime {
            return OffsetDateTime.now()
        }

        fun getStartDateOfMonth(month: Int): String {
            val cal: Calendar = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = month
            if (month < 10) {
                return "01-0$month-$year"

            } else {
                return "01-$month-$year"

            }


        }

        fun getLastDateOFMonth(month: Int): Int {

            if(month==2){
                return 28
            }else{
                val cal: Calendar = Calendar.getInstance()
                val year: Int = cal.get(Calendar.YEAR)
                cal.set(Calendar.MONTH, month-1)
                val date: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                return date
            }

        }


        fun getCurrentMonth(): Int {
            val calendar: Calendar = Calendar.getInstance()
            return calendar.get(Calendar.MONTH) + 1
        }

        fun getCurrentDate(): Int {
            val calendar: Calendar = Calendar.getInstance()
            return calendar.get(Calendar.DAY_OF_MONTH)
        }


        fun getFirstOffSetDateOfMonth(month: Int, year: Int): OffsetDateTime {

            return OffsetDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        }


        fun getHomeOffSetDateOfMonth(date:Int,month: Int, year: Int): OffsetDateTime {

            return OffsetDateTime.of(year, month, date, 0, 0, 0, 0, ZoneOffset.UTC)
        }



        fun getLastOffsetDateOfMonth(month: Int, year: Int, lastDate: Int): OffsetDateTime {

            return OffsetDateTime.of(year, month, lastDate, 0, 0, 0, 0, ZoneOffset.UTC)
        }


        fun getCategoryList():MutableList<String>{
            val  list = mutableListOf<String>()
            list.add(Constant.OTHER)
            list.add(Constant.FAST_FOOD)
            list.add(Constant.CLOTH)
            list.add(Constant.TUITION)
            list.add(Constant.ELECTRIC)
            list.add(Constant.VEGETABLES)
            list.add(Constant.RECHARGE)
            list.add(Constant.FARE)
            list.add(Constant.FAST_FOOD)

            return  list
        }


    }
}