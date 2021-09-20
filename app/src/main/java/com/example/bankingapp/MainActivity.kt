package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bankingapp.data.UserDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mDb = UserDatabase.getInstance(this)
    }
    fun onViewUserListClick(view:View){
        val intent = Intent(this,UserListActivity::class.java)
        startActivity(intent)
    }
}