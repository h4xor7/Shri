package com.pandey.shri.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pandey.shri.data.model.Entry
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Dao
interface ExpenseDao {

   // private val date1 = OffsetDateTime.of(2021, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC)
  // val stringToTest = "2021-01-01T01:00:00+00:00"


    @Query("SELECT * FROM entries ORDER BY date ASC ")
    fun getAll(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getDataByMonth(startDate:OffsetDateTime,endDate:OffsetDateTime): LiveData<List<Entry>>


   // date startTime will be the timestamp for 00:00, i.e., 09/11/2017 00:00:00

   // date endTime will be the timestamp for 23:59, i.e., 09/11/2017 23:59:59

/*
    @Query("SELECT * FROM entries WHERE date BETWEEN :startTime AND :endTime ORDER BY date ASC")
    fun getDataByDate(startTime:OffsetDateTime,endTime:OffsetDateTime): LiveData<List<Entry>>
*/


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertALL(entry: Entry)



    @Query("SELECT * FROM entries WHERE category = :categoryName AND date  BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getDataByCategory(categoryName:String,startDate:OffsetDateTime,endDate:OffsetDateTime): LiveData<List<Entry>>





}