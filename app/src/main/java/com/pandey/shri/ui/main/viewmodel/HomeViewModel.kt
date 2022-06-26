package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseDetailRepository
import java.time.OffsetDateTime

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseDetailRepository



    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        repository = ExpenseDetailRepository(expenseDao)
    }



    fun getFilterData(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        return repository.filterDatabase(fromDateTime, toDateTime)
    }

   suspend fun getDataByCategory(categoryName:String,fromDateTime: OffsetDateTime,
                          toDateTime: OffsetDateTime) :List<Entry>{

        return repository.getDataByCategory(categoryName,fromDateTime,toDateTime)
    }



}