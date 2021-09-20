package com.example.bankingapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.bankingapp.data.Transaction
import com.example.bankingapp.data.UserDatabase
import java.util.*
import kotlin.properties.Delegates

class TransactionActivity : AppCompatActivity() {

    lateinit var userDb : UserDatabase
    var senderAccNo by Delegates.notNull<Long>()
    var receiverAccNo by Delegates.notNull<Long>()
    var availableBal by Delegates.notNull<Double>()
    lateinit var sender:String
    lateinit var receiver: String
    lateinit var amount_edit_text:EditText
    lateinit var layout : ConstraintLayout
    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        title = "Confirm and Pay"
        layout = findViewById(R.id.trans_layout)
        progressBar = findViewById(R.id.progress)
        userDb = UserDatabase.getInstance(this)
        val senderNameTv  = findViewById<TextView>(R.id.sender_tv)
        val receiverNameTv = findViewById<TextView>(R.id.receiver_tv)
        val receiverAccTv = findViewById<TextView>(R.id.receriver_accno_tv)
        val senderAccTv = findViewById<TextView>(R.id.sender_accno_tv)
        val available_bal = findViewById<TextView>(R.id.available_bal)
         amount_edit_text = findViewById<EditText>(R.id.amountEditText)
        val sendButton = findViewById<Button>(R.id.sendMoneyButton)
//        val errorMessage = findViewById<TextView>(R.id.invalid_amount)
//        errorMessage.visibility = View.GONE
//        sendButton.isEnabled = false

         sender = intent.getStringExtra("sender").toString()
         receiver = intent.getStringExtra("receiver").toString()
         senderAccNo = intent.getLongExtra("sender_acc",0)
         receiverAccNo = intent.getLongExtra("receiver_acc",0)
         availableBal = intent.getDoubleExtra("available_bal",0.0)

        senderNameTv.text = sender
        receiverNameTv.text = receiver
        senderAccTv.text = "Acc no: ${senderAccNo}"
        receiverAccTv.text = "Acc no: ${receiverAccNo}"
        available_bal.text="Available balance : Rs ${availableBal}/-"
//        val textWatcher = object : TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                sendButton.isEnabled = amount_edit_text.text.isNotEmpty() &&  amount_edit_text.text.toString().toDouble() <= availableBal && amount_edit_text.text.toString().toDouble() > 0
////                if (amount_edit_text.text.isNotEmpty()) {
////                    if (amount_edit_text.text.toString().toDouble() > availableBal || amount_edit_text.text.toString().toDouble() == 0.0   )
////                        errorMessage.visibility = View.VISIBLE
////                    else
////                        errorMessage.visibility = View.GONE
////                }else{
////                    errorMessage.visibility = View.GONE
////                }
//            }
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        }

//        amount_edit_text.addTextChangedListener(textWatcher)
    }

    fun onSendClicked(view : View ){
        if(!isAmoutValid(amount_edit_text.text)){
            amount_edit_text.error = "Enter valid amount"
        }else {
            amount_edit_text.error = null
            layout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            Toast.makeText(this, "Payment processing..", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable {
                kotlin.run {
                    progressBar.visibility = View.GONE
                    val transaction_uid = UUID.randomUUID().toString()
                    val date = Calendar.getInstance().time
                    val transactionAmount = amount_edit_text.text.toString().toDouble()
                    val dialog = Dialog(this)
                    ThreadExecutor.getInstance()?.diskIO?.execute(Runnable {
                        kotlin.run {
                            userDb.transactionDao().insertTransaction(
                                Transaction(
                                    sender, receiver,
                                    senderAccNo, receiverAccNo,
                                    transactionAmount,
                                    transaction_uid,
                                    date
                                )
                            )
                            val receiverBalance =
                                userDb.userDao().getUserByAccountNo(receiverAccNo).current_balance
                            userDb.userDao().updateUserCurrentBalance(
                                senderAccNo,
                                availableBal - transactionAmount
                            )
                            userDb.userDao().updateUserCurrentBalance(
                                receiverAccNo,
                                receiverBalance + transactionAmount
                            )
                            runOnUiThread(Runnable {
                                kotlin.run {

                                    dialog.setContentView(R.layout.custom_diolog)
                                    val doneButton = dialog.findViewById<Button>(R.id.done_button)
                                    val senderInfo = dialog.findViewById<TextView>(R.id.sender_info)
                                    val receiverInfo =
                                        dialog.findViewById<TextView>(R.id.receiver_info)
                                    val transaction_amt =
                                        dialog.findViewById<TextView>(R.id.transaction_amount)
                                    val transaction_id_tv =
                                        dialog.findViewById<TextView>(R.id.transaction_id)
                                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    senderInfo.text = "From: $sender Acc no. $senderAccNo"
                                    receiverInfo.text = "To: $receiver Acc no. $receiverAccNo"
                                    transaction_amt.text = "Rs $transactionAmount"
                                    transaction_id_tv.text = "Transaction id : $transaction_uid"
                                    doneButton.setOnClickListener {
                                        val intent = Intent(this, UserListActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        dialog.dismiss()
                                        startActivity(intent)
                                    }
                                    dialog.setCanceledOnTouchOutside(false)
                                    dialog.show()
                                }
                            })
                        }
                    })
                }
            }, 1500)
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert!")
        builder.setMessage("Do you want to cancel the transaction?")
        builder.setPositiveButton("Yes") { dialog, which ->
            val intent= Intent(this,UserListActivity::class.java)
            intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        builder.setNegativeButton("No"){dialog,which ->
        }
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    fun isAmoutValid(amount : Editable?):Boolean{
        return if (amount?.isNotEmpty() == true) {
            if (amount.toString().toDouble() > availableBal || amount_edit_text.text.toString().toDouble() == 0.0   )
               false
            else
              true
        }else{
            false
        }
    }
}