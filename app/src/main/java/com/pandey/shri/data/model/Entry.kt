package com.pandey.shri.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "entries")
data class Entry(
    @ColumnInfo(name = "date")  val date:Long,
    @ColumnInfo(name = "category") val  category: String,
    @ColumnInfo(name = "item_name") val itemName:String,
    @ColumnInfo(name = "item_price") val itemPrice:Int

    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
