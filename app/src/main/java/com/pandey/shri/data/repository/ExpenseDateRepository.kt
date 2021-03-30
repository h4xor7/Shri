package com.pandey.shri.data.repository

import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry
import java.time.OffsetDateTime

class ExpenseDateRepository(private val expenseDao: ExpenseDao, fromDateTime: OffsetDateTime,
                            toDateTime: OffsetDateTime) {
    fun filterDatabase(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        return expenseDao.getDataByMonth(fromDateTime,toDateTime)
    }

    val dataByMonth: LiveData<List<Entry>> = expenseDao.getDataByMonth(fromDateTime,toDateTime)

}