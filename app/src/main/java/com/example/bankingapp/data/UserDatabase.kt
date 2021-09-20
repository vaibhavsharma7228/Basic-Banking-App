package com.example.bankingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bankingapp.ThreadExecutor

@Database(entities = [User::class,Transaction::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao

    abstract fun transactionDao() : TransactionDao

    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): UserDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }
//Dummy data for 10 users having initial balance Rs 5000.00
        private fun populateDatabase(db: UserDatabase) {
            val userDao = db.userDao()
            ThreadExecutor.getInstance()?.diskIO?.execute(Runnable {
                kotlin.run {
                userDao.insertUser(User("Rahul Das","rahuldas@example.com",1102476901,5000.00,"A12B34C",81234))
                userDao.insertUser(User("Aditya Sharma","aditya@example.com",1119568403,5000.00,"D12E34F",81235))
                userDao.insertUser(User("Vijay Sharma","vijay@example.com",1124295701,5000.00,"G12H34I",91236))
                userDao.insertUser(User("Kapil Ahuja","kapil@example.com",1132395740,5000.00,"J12K34L",81237))
                userDao.insertUser(User("Vaibhav Sharma","vaibhav@example.com",1140974812,5000.00,"M45N67O",91238))
                userDao.insertUser(User("Neeraj Kumar","neeraj@example.com",1154520958,5000.00,"P45Q67R",71239))
                userDao.insertUser(User("Shruti Tiwari","shruti@example.com",1167892321,5000.00,"S45T67U",81230))
                userDao.insertUser(User("Ishita Sethi","ishita@example.com",1172074867,5000.00,"V78W90X",91231))
                userDao.insertUser(User("Aditi Sharma","aditi@example.com",1180264876,5000.00,"Y78Z90A",71232))
                userDao.insertUser(User("Garima Bajaj","garima@example.com",1191092384,5000.00,"Y78Z90B",91233))}
            })
        }
    }
}