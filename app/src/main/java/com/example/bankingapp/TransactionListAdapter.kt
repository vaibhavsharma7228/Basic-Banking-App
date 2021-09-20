package com.example.bankingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat


class TransactionListAdapter(private val transactions: List<com.example.bankingapp.data.Transaction>,onTransactionClickListener: OnTransactionClickListener) :
        RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {


    val mTransClickListener = onTransactionClickListener
    public interface OnTransactionClickListener{
        fun onTransClick(trans_id:String)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.transaction_history_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.time.text = transactions.get(position).transaction_date
        val sdf = SimpleDateFormat("EEE, d MMM yyyy h:mm a")
        val dateTime: String = sdf.format(transactions.get(position).transaction_date)
        viewHolder.time.text = dateTime
        viewHolder.trans_amt.text = " Rs ${transactions.get(position).transaction_amount}"
        viewHolder.sender.text = transactions.get(position).sender
        viewHolder.receiver.text = transactions.get(position).receiver
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = transactions.size
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val sender: TextView
        val receiver : TextView
        val time : TextView
        val trans_amt :TextView

        init {
            // Define click listener for the ViewHolder's View.
            sender = view.findViewById(R.id.sender)
            receiver = view.findViewById(R.id.receiver)
            time = view.findViewById(R.id.date_tv)
            trans_amt = view.findViewById(R.id.amount_tv)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val trans_selected = transactions.get(adapterPosition)
            val trans_id = trans_selected.transaction_id
            mTransClickListener.onTransClick(trans_id)
        }
    }

}
