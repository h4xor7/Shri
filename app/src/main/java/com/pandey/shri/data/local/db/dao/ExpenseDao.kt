package com.pandey.shri.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pandey.shri.data.model.Entry
import java.time.OffsetDateTime

@Dao
interface ExpenseDao {



    @Query("SELECT * FROM entries ORDER BY date ASC ")
    fun getAll(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getDataByMonth(startDate:OffsetDateTime,endDate:OffsetDateTime): LiveData<List<Entry>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun  insertALL(entry: Entry)

    @Delete
     fun  deleteExpense(entry: Entry)



    @Query("SELECT * FROM entries WHERE category = :categoryName AND date  BETWEEN :startDate AND :endDate ORDER BY date ASC")
      fun getDataByCategory(categoryName:String,startDate:OffsetDateTime,endDate:OffsetDateTime): List<Entry>





}