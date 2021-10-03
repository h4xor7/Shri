package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseRepository
import com.pandey.shri.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository
    val allExpense: LiveData<List<Entry>>
    private var monthValue = Utils.getCurrentMonth()
    private var yearValue = Calendar.getInstance().get(Calendar.YEAR)
    private var dateValue = 1


    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpense = repository.allExpense
        Log.d(Companion.TAG, ": $allExpense")
    }

    fun getHomeFilterData(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        return repository.filterDatabase(fromDateTime, toDateTime)
    }

    fun getDataByCategory(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime, categoryName: String
    ): List<Entry> {

        return repository.getDataByCategory(categoryName, fromDateTime, toDateTime)
    }






    companion object {
        private const val TAG = "HomeViewModel"
    }


}