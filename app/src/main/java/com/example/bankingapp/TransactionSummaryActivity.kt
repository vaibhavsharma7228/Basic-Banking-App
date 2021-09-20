package com.example.bankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.bankingapp.data.Transaction
import com.example.bankingapp.data.UserDatabase
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat

class TransactionSummaryActivity : AppCompatActivity() {
    lateinit var sender_tv : TextView
    lateinit var receiver_tv : TextView
    lateinit var trans_amt_head :TextView
    lateinit var trans_amt_tv : TextView
    lateinit var trans_id_tv:TextView
    lateinit var trans_date_tv :TextView
    lateinit var userDb : UserDatabase
    lateinit var transaction : Transaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_summary)

        val toolbar = findViewById<MaterialToolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sender_tv = findViewById(R.id.sender_info)
        receiver_tv = findViewById(R.id.receiver_info)
        trans_amt_head = findViewById(R.id.trans_amt_head)
        trans_amt_tv = findViewById(R.id.trans_amt_tv)
        trans_id_tv = findViewById(R.id.transaction_id)
        trans_date_tv = findViewById(R.id.transaction_date)
        userDb = UserDatabase.getInstance(this)

        val trans_id_clicked = intent.getStringExtra("trans_id")
        showSummary(trans_id_clicked!!)
    }

    fun showSummary(transId : String){
        ThreadExecutor.getInstance()?.diskIO?.execute(Runnable { kotlin.run {
            transaction = userDb.transactionDao().getTransactionById(transId)
            runOnUiThread(Runnable { kotlin.run {
                sender_tv.text = "${transaction.sender} Acc no. ${transaction.sender_accno}"
                receiver_tv.text = "${transaction.receiver} Acc no. ${transaction.receiver_accno}"
                trans_amt_head.text = "Rs ${transaction.transaction_amount}"
                trans_amt_tv.text = "Paid Rs ${transaction.transaction_amount}"
                trans_id_tv.text = transaction.transaction_id
                val sdf = SimpleDateFormat("EEE, d MMM yyyy h:mm a")
                val dateTime: String = sdf.format(transaction.transaction_date)
                trans_date_tv.text = dateTime
            } })
        } })
    }
}