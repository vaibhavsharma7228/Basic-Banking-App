package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.data.User
import com.example.bankingapp.data.UserDatabase
import kotlin.properties.Delegates

class ReceiversListActivity : AppCompatActivity(),ReceiverListAdapter.OnSendButtonClickListener {

    val layoutManager :LinearLayoutManager
    lateinit var usersDb :UserDatabase
    lateinit var mAdapter : ReceiverListAdapter
    lateinit var receiversList : List<User>
    var acc_no by Delegates.notNull<Long>()
    lateinit var username : String
    var current_bal by Delegates.notNull<Double>()

    init {
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receivers_list)
        title = "Receivers"
        usersDb = UserDatabase.getInstance(this)
         acc_no = intent.getLongExtra("acc_no",0)
         username = intent.getStringExtra("username").toString()
        current_bal = intent.getDoubleExtra("available_bal",0.0)
        val username_tv = findViewById<TextView>(R.id.username_tv)
        username_tv.text = username
        val  mRecyclerView = findViewById<RecyclerView>(R.id.receiver_list_rv)
        ThreadExecutor.getInstance()?.diskIO?.execute(Runnable {
            kotlin.run {
                receiversList = usersDb.userDao().getReceivers(acc_no)
                runOnUiThread(Runnable {
                    kotlin.run {
                        mRecyclerView.layoutManager = layoutManager
                        mAdapter = ReceiverListAdapter(receiversList,this)
                        mRecyclerView.adapter = mAdapter
                    }
                })
            }
        })
    }

    override fun onSendClicked(receiverUsername: String, receiverAccNo: Long) {
        val intentToTransaction = Intent(this,TransactionActivity::class.java)
        intentToTransaction.putExtra("sender",username)
        intentToTransaction.putExtra("receiver",receiverUsername)
        intentToTransaction.putExtra("sender_acc",acc_no)
        intentToTransaction.putExtra("receiver_acc",receiverAccNo)
        intentToTransaction.putExtra("available_bal",current_bal)
        startActivity(intentToTransaction)
    }
}