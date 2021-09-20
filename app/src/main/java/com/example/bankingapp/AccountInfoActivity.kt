package com.example.bankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.bankingapp.data.User
import com.example.bankingapp.data.UserDatabase
import com.google.android.material.appbar.MaterialToolbar
import kotlin.properties.Delegates

class AccountInfoActivity : AppCompatActivity() {
     var acc_number_from_intent by Delegates.notNull<Long>()
    lateinit var user :User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        val toolbar = findViewById<MaterialToolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username_tv = findViewById<TextView>(R.id.username_tv)
        val email_tv = findViewById<TextView>(R.id.email_tv)
        val acc_no = findViewById<TextView>(R.id.acc_no_tv)
        val ifsc_tv = findViewById<TextView>(R.id.ifsc_tv)
        val mob_no = findViewById<TextView>(R.id.mobile_no_tv)
        val current_bal_tv = findViewById<TextView>(R.id.current_balance_tv)
        val userDb = UserDatabase.getInstance(this)
         acc_number_from_intent = intent.getLongExtra("acc_no",0)


        ThreadExecutor.getInstance()?.diskIO?.execute(Runnable {
            kotlin.run {
                user = userDb.userDao().getUserByAccountNo(acc_number_from_intent)
                runOnUiThread(Runnable {
                    kotlin.run {
                        username_tv.text = user.name
                        email_tv.text = user.email
                        acc_no.text = user.acc_no.toString()
                        ifsc_tv.text = user.ifsc_code
                        mob_no.text = user.mobile_no.toString()
                        current_bal_tv.text = " Rs ${user.current_balance} /-"
                    }
                })
            }
        })
    }
    fun transferClicked(view: View){
        val intentToReceivers = Intent(this,ReceiversListActivity::class.java)
        intentToReceivers.putExtra("acc_no",acc_number_from_intent)
        intentToReceivers.putExtra("username",user.name)
        intentToReceivers.putExtra("available_bal",user.current_balance)
        startActivity(intentToReceivers)
    }
}