package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: ExpenseRepository

    val allExpense: LiveData<List<Entry>>


    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpense = repository.allExpense.asLiveData()

    }

    fun getDataByDate(
        fromDate: Long,
        toDate: Long
    ): LiveData<List<Entry>> {
        return repository.getDataByDate(fromDate,toDate).asLiveData()
    }

}