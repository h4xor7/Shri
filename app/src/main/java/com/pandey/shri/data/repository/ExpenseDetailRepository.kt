package com.pandey.shri.data.repository

import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry
import java.time.OffsetDateTime

class ExpenseDetailRepository(private val expenseDao: ExpenseDao) {
    fun filterDatabase(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        //return expenseDao.getOrderByCategory(fromDateTime,toDateTime)
        return expenseDao.getDataByMonth(fromDateTime,toDateTime)
    }

    //val dataByMonth: LiveData<List<Entry>> = expenseDao.getDataByMonth(fromDateTime,toDateTime)

    //delete all data from database
    suspend fun deleteExpense(entry: Entry) {
        expenseDao.deleteExpense(entry)
    }

     suspend fun  getDataByCategory(categoryName:String,fromDateTime: OffsetDateTime,
                                   toDateTime: OffsetDateTime) : List<Entry>{
       return expenseDao.getDataByCategory(categoryName,fromDateTime,toDateTime)
    }

    suspend fun insert(entry: Entry) {
        expenseDao.insertALL(entry)
    }

}