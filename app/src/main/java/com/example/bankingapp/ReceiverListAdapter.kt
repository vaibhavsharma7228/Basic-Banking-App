package com.example.bankingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.data.User


class ReceiverListAdapter(private val recieverList: List<User>,onSendButtonClickListener: OnSendButtonClickListener) :
    RecyclerView.Adapter<ReceiverListAdapter.ViewHolder>() {


    private val  mOnSendClickListener  = onSendButtonClickListener
    public interface OnSendButtonClickListener {
        fun onSendClicked(receiverUsername : String , receiverAccNo : Long)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.reciever_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.acc_no.text = "Acc no. : ${recieverList.get(position).acc_no}"
        viewHolder.username_tv.text = recieverList.get(position).name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = recieverList.size

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val username_tv: TextView
        val acc_no:TextView
        val sendButton : Button

        init {
            // Define click listener for the ViewHolder's View.
            username_tv = view.findViewById(R.id.username_tv)
            acc_no = view.findViewById(R.id.acc_no_tv)
            sendButton = view.findViewById(R.id.send_button)
            sendButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val username =  recieverList.get(adapterPosition).name
            val acc_no= recieverList.get(adapterPosition).acc_no
            mOnSendClickListener.onSendClicked(username,acc_no)
        }
    }

}
