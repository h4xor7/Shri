package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseDetailRepository


    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        repository = ExpenseDetailRepository(expenseDao)

    }



     fun insert(entry: Entry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entry)
    }


}