package com.pandey.shri.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pandey.shri.data.model.Entry
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Dao
interface ExpenseDao {



    @Query("SELECT * FROM entries ORDER BY date ASC ")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE date BETWEEN :fromDate AND :toDate ORDER BY date ASC")
    fun getDataByDate(fromDate:Long,toDate:Long): Flow<List<Entry>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertALL(entry: Entry)



    @Query("SELECT * FROM entries WHERE category = :categoryName AND date  BETWEEN :fromDate AND :toDate ORDER BY date ASC")
    fun getDataByCategory(fromDate:Long,toDate:Long,categoryName:String,): Flow<List<Entry>>





}