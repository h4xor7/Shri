package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseDateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.OffsetDateTime

class DetailDateViewModel(
    application: Application,
    fromDateTime: OffsetDateTime,
    toDateTime: OffsetDateTime
) : AndroidViewModel(application) {
    private val mrepository: ExpenseDateRepository
    val dataByMonth: LiveData<List<Entry>>

    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        mrepository = ExpenseDateRepository(expenseDao, fromDateTime, toDateTime)
        Log.d(Companion.TAG, ":${mrepository.dataByMonth} ")
        dataByMonth = mrepository.dataByMonth

    }

    fun getFilterData( fromDateTime: OffsetDateTime,
                       toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  mrepository.filterDatabase(fromDateTime,toDateTime)
    }




        companion object {
        private const val TAG = "DetailDateViewModel"
    }
}