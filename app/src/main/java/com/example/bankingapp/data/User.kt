package com.example.bankingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(val name:String,
                val email:String,
                val acc_no:Long,
                val current_balance : Double,
                val ifsc_code:String,
                val mobile_no:Long,
         @PrimaryKey(autoGenerate = true) val id : Int?=null)