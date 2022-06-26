package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class DetailDateViewModel(
    application: Application,

    ) : AndroidViewModel(application) {
    private val mrepository: ExpenseDetailRepository

    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        mrepository = ExpenseDetailRepository(expenseDao)
    }

    fun getFilterData(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        return mrepository.filterDatabase(fromDateTime, toDateTime)
    }


    fun deleteExpense(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO) {
            mrepository.deleteExpense(entry)
        }
    }


    companion object {
        private const val TAG = "DetailDateViewModel"
    }
}