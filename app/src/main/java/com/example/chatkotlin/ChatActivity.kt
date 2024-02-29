package com.example.chatkotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecycle: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendbutton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagelist: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference
    var reciverroom: String? = null
    var senderroom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val intent = intent
        val name = intent.getStringExtra("name")

        val reciveruid = intent.getStringExtra("uidz")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance().reference

        senderroom = reciveruid + senderuid
        reciverroom = senderuid + reciveruid

        chatRecycle = findViewById(R.id.charRecyclerView)
        messageBox = findViewById(R.id.messagebox)
        sendbutton = findViewById(R.id.sendbuttons)
        messagelist = ArrayList()
        messageAdapter = MessageAdapter(this, messagelist)

        chatRecycle.layoutManager = LinearLayoutManager(this)
        chatRecycle.adapter = messageAdapter


        mDbRef.child("chats").child(senderroom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })




        sendbutton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderuid)

            mDbRef.child("chats").child(senderroom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(reciverroom!!).child("messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")
        }
    }
}