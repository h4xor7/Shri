package com.pandey.shri.utils

class Utils {
    companion object{

        fun getMonthName(intMonth: Int): String {

            return when (intMonth) {
                0 -> "JAN"
                1 -> "FEB"
                2 -> "MAR"
                3 -> "APR"
                4 -> "MAY"
                5 -> "JUN"
                6 -> "JUL"
                7 -> "AUG"
                8 -> "SEP"
                9 -> "OCT"
                10 -> "NOV"
                else -> {
                    "DEC"
                }
            }
        }

    }
}