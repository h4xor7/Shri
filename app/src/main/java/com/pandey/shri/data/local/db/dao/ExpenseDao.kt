package com.pandey.shri.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pandey.shri.data.model.Entry

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM entries")
    fun getAll(): LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertALL(entry: Entry)

}