package com.pandey.shri.data.local.db

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Converters {
    private val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
   // private val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime {
        return value?.let {
            dateFormatter.parse(it, OffsetDateTime::from)
        } ?: OffsetDateTime.now()
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime): String {
        return dateFormatter.format(date)
    }
}