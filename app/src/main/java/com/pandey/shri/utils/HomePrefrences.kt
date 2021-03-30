package com.pandey.shri.utils

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomePrefrences(private val context: Context) {

    private val dataStore = context.createDataStore(
        name = "HomePrefs"
    )

    companion object {
        val GRAND_TOTAL_KEY = preferencesKey<Int>(Constant.GRAND_TOTAL_KEY)
        val RECHARGE_KEY = preferencesKey<Int>(Constant.RECHARGE_KEY)
        val VEGETABLES_KEY = preferencesKey<Int>(Constant.VEGETABLES_KEY)
        val CLOTH_KEY = preferencesKey<Int>(Constant.CLOTH_KEY)
        val FAST_FOOD_KEY = preferencesKey<Int>(Constant.FAST_FOOD_KEY)
        val FARE_KEY = preferencesKey<Int>(Constant.FARE_KEY)
        val ELECTRIC_KEY = preferencesKey<Int>(Constant.ELECTRIC_KEY)
        val TUITION_KEY = preferencesKey<Int>(Constant.TUITION_KEY)
        val OTHER_KEY = preferencesKey<Int>(Constant.OTHER_KEY)
    }


    suspend fun saveGrandTotal(grandTotal: Int) {
        dataStore.edit {
            it[GRAND_TOTAL_KEY] = grandTotal
        }

    }

    val getGrandTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[GRAND_TOTAL_KEY] ?: 0
    }






    suspend fun saveRechargeTotal(rechargeTotal: Int) {
        dataStore.edit {
            it[RECHARGE_KEY] = rechargeTotal
        }

    }

    val getRechargeTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[RECHARGE_KEY] ?: 0
    }



    suspend fun saveFastFoodTotal(fastFoodTotal: Int) {
        dataStore.edit {
            it[FAST_FOOD_KEY] = fastFoodTotal
        }

    }

    val getFastFoodTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[FAST_FOOD_KEY] ?: 0
    }



    suspend fun saveFareTotal(fareTotal: Int) {
        dataStore.edit {
            it[FARE_KEY] = fareTotal
        }
    }

    val getFareTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[FARE_KEY] ?: 0
    }



    suspend fun saveElectricTotal(electricTotal: Int) {
        dataStore.edit {
            it[ELECTRIC_KEY] = electricTotal
        }
    }

    val getElectricTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[ELECTRIC_KEY] ?: 0
    }



    suspend fun saveTuitionTotal(tuitionTotal: Int) {
        dataStore.edit {
            it[TUITION_KEY] = tuitionTotal
        }
    }

    val getTuitionTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[TUITION_KEY] ?: 0
    }


    suspend fun saveOtherTotal(otherTotal: Int) {
        dataStore.edit {
            it[OTHER_KEY] = otherTotal
        }
    }

    val getOtherTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[OTHER_KEY] ?: 0
    }





    suspend fun saveVegetableTotal(vegetableTotal: Int) {
        dataStore.edit {
            it[VEGETABLES_KEY] = vegetableTotal
        }

    }

    val getVegetableTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[VEGETABLES_KEY] ?: 0
    }





    suspend fun saveClothTotal(clothTotal: Int) {
        dataStore.edit {
            it[CLOTH_KEY] = clothTotal
        }

    }

    val getClothTotal: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[CLOTH_KEY] ?: 0
    }





}