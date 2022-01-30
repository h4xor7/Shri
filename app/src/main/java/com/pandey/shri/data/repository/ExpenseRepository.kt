package com.pandey.shri.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry
import com.pandey.shri.utils.Constant
import com.pandey.shri.utils.Utils
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

class ExpenseRepository(private val expenseDao: ExpenseDao) {




    val allExpense: Flow<List<Entry>> = expenseDao.getAll()




    fun getDataByDate(
        fromDate: Long,
        toDate: Long
    ): Flow<List<Entry>> {
        return expenseDao.getDataByDate(fromDate,toDate)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entry: Entry) {
        expenseDao.insertALL(entry)
    }

    fun getDataByCategory(
        fromDate: Long,
        toDate: Long, categoryName: String,
    ): Flow<List<Entry>> {


       return expenseDao.getDataByCategory(fromDate,toDate,categoryName)
    }
}