package com.pandey.shri.data.repository

import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry

class ExpenseRepository(private val expenseDao: ExpenseDao) {


    val allExpense:LiveData<List<Entry>> = expenseDao.getAll()


    suspend fun insert(entry: Entry) {
        expenseDao.insertALL(entry)
    }
}