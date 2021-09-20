package com.example.bankingapp.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun getDateFromLong(date : Long?):Date?{
        return if(date == null) null else Date(date)
    }
    @TypeConverter
    fun getLongFromDate(date : Date?):Long?{
        return if(date == null ) null else date.time
    }
}