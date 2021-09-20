package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Transaction
import com.example.bankingapp.data.UserDatabase
import com.google.android.material.appbar.MaterialToolbar

class TransactionHistoryActivity : AppCompatActivity(),TransactionListAdapter.OnTransactionClickListener {
    lateinit var transactions : List<com.example.bankingapp.data.Transaction>
    lateinit var userDb : UserDatabase
    lateinit var mAdapter: TransactionListAdapter
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val toolbar = findViewById<MaterialToolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userDb = UserDatabase.getInstance(this)
        mRecyclerView = findViewById(R.id.trans_history_rv)

        ThreadExecutor.getInstance()?.diskIO?.execute(Runnable { kotlin.run {
            transactions = userDb.transactionDao().getAllTransactions()
            runOnUiThread(Runnable { kotlin.run {
                val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                mAdapter = TransactionListAdapter(transactions,this)
                mRecyclerView.adapter = mAdapter
                mRecyclerView.layoutManager = layoutManager
            } })
        } })



    }

    override fun onTransClick(trans_id: String) {
        val intentToSummary = Intent(this,TransactionSummaryActivity::class.java)
        intentToSummary.putExtra("trans_id",trans_id)
        startActivity(intentToSummary)
    }
}