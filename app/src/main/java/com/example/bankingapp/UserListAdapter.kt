package com.example.bankingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.data.User


class UserListAdapter(private val userList: List<User>?, onUserClickListener: OnUserClickListener) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    val mOnUserClickListener = onUserClickListener
    public interface OnUserClickListener{
        fun onUserClick(acc_no : Long)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_user_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.username_tv.text = userList?.get(position)?.name
        viewHolder.accno_tv.text = userList?.get(position)?.acc_no.toString()
        viewHolder.current_balance_tv.text = " Rs ${userList?.get(position)?.current_balance}"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList?.size ?: 0
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val accno_tv: TextView
        val username_tv : TextView
        val current_balance_tv : TextView
        init {
            // Define click listener for the ViewHolder's View.
            accno_tv = view.findViewById(R.id.acc_no_tv)
            username_tv = view.findViewById(R.id.username_tv)
            current_balance_tv = view.findViewById(R.id.current_balance)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val acc_no = userList?.get(adapterPosition)?.acc_no ?: 0
            mOnUserClickListener.onUserClick(acc_no)
        }
    }
}
