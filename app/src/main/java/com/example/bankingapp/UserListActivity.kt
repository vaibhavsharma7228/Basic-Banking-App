package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.data.User
import com.example.bankingapp.data.UserDatabase
import com.google.android.material.appbar.MaterialToolbar

class UserListActivity : AppCompatActivity(),UserListAdapter.OnUserClickListener {
    private lateinit var mRecyclerView :RecyclerView
    private lateinit var mAdapter : UserListAdapter
    lateinit var userDb :UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        val toolbar = findViewById<MaterialToolbar>(R.id.app_bar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userDb  = UserDatabase.getInstance(this)
        mRecyclerView = findViewById(R.id.user_list_rv)
         var userList : List<User>?
        ThreadExecutor.getInstance()?.diskIO?.execute(Runnable { kotlin.run {
            userList = userDb.userDao().getAllUsers()
            runOnUiThread(
                Runnable { kotlin.run {
                    val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                    mAdapter = UserListAdapter(userList,this)
                    mRecyclerView.adapter = mAdapter
                    mRecyclerView.layoutManager = layoutManager
                } }
            )
        } })
    }

    override fun onUserClick(acc_no: Long) {
        val intentToAccInfo = Intent(this,AccountInfoActivity::class.java)
        intentToAccInfo.putExtra("acc_no",acc_no)
        startActivity(intentToAccInfo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.transaction_history)
        {
            val intent = Intent(this,TransactionHistoryActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}