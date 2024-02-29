package com.example.chatkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messagelist: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Item_Recive = 1;
    val Item_Sent = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.recive, parent, false)
            return ReciveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messagelist[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return Item_Sent
        } else {
            return Item_Recive
        }
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messagelist[position]
        if (holder.javaClass == SentViewHolder::class.java) {

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReciveViewHolder
            holder.reciveMessage.text = currentMessage.message
        }
    }

    class SentViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val sentMessage = itemview.findViewById<TextView>(R.id.text_sent)
    }

    class ReciveViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val reciveMessage = itemview.findViewById<TextView>(R.id.text_recive)

    }
}