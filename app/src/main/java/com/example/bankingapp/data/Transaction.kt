package com.example.bankingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "transaction_table")

data class Transaction(
        val sender : String,
        val receiver:String,
        val sender_accno : Long,
        val receiver_accno : Long,
        val transaction_amount : Double,
        @TypeConverters(DateConverter::class)
        @PrimaryKey(autoGenerate = false)
        val transaction_id : String,
        val transaction_date : Date
)
