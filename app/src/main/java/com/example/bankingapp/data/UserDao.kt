package com.example.bankingapp.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users_table ")
    fun getAllUsers():List<User>

    @Insert
    fun insertUser(user : User)

    @Query("UPDATE users_table SET current_balance = :updatedBalance WHERE acc_no = :account_no")
    fun updateUserCurrentBalance(account_no: Long,updatedBalance : Double)

    @Delete
    fun deleteUser(user:User)

    @Query("SELECT * FROM users_table WHERE acc_no = :account_no")
    fun getUserByAccountNo(account_no : Long):User

    @Query("SELECT * FROM users_table WHERE acc_no != :account_no" )
    fun getReceivers(account_no: Long):List<User>

}