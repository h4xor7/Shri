package com.pandey.shri.data.repository

import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.dao.ExpenseDao
import com.pandey.shri.data.model.Entry
import com.pandey.shri.utils.Constant
import com.pandey.shri.utils.Utils
import java.time.OffsetDateTime

class ExpenseRepository(private val expenseDao: ExpenseDao) {




    val allExpense: LiveData<List<Entry>> = expenseDao.getAll()




    fun filterDatabase(
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): LiveData<List<Entry>> {
        return expenseDao.getDataByMonth(fromDateTime,toDateTime)
    }

    fun allCloth(  fromDateTime: OffsetDateTime,
                   toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.CLOTH,fromDateTime,toDateTime)
    }

    fun allTuition(  fromDateTime: OffsetDateTime,
                   toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.TUITION,fromDateTime,toDateTime)
    }


    fun allOther(  fromDateTime: OffsetDateTime,
                      toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.OTHER,fromDateTime,toDateTime)
    }

    fun allRecharge(  fromDateTime: OffsetDateTime,
                      toDateTime: OffsetDateTime): List<Entry>{
       return  expenseDao.getDataByCategory(Constant.RECHARGE,fromDateTime,toDateTime)
   }
    fun allVegetable(  fromDateTime: OffsetDateTime,
                       toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.VEGETABLES,fromDateTime,toDateTime)
    }

    fun allFastFood(  fromDateTime: OffsetDateTime,
                      toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.FAST_FOOD,fromDateTime,toDateTime)
    }
    fun allFare(  fromDateTime: OffsetDateTime,
                  toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.FARE,fromDateTime,toDateTime)
    }

    fun allElectric(  fromDateTime: OffsetDateTime,
                      toDateTime: OffsetDateTime): List<Entry>{
        return  expenseDao.getDataByCategory(Constant.ELECTRIC,fromDateTime,toDateTime)
    }


    suspend fun insert(entry: Entry) {
        expenseDao.insertALL(entry)
    }

    fun getDataByCategory(
        categoryName: String,
        fromDateTime: OffsetDateTime,
        toDateTime: OffsetDateTime
    ): List<Entry> {


       return expenseDao.getDataByCategory(categoryName,fromDateTime,toDateTime)
    }
}