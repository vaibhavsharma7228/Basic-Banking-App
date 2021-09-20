package com.example.bankingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    fun insertTransaction(transactionDao: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY transaction_date desc")
    fun getAllTransactions():List<Transaction>

    @Query("SELECT * FROM transaction_table WHERE transaction_id = :trans_id")
    fun getTransactionById(trans_id : String):Transaction
}