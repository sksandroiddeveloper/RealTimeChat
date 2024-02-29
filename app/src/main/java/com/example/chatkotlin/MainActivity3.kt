package com.example.chatkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_main3)
        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (auth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        userRecyclerView = findViewById(R.id.userRecycler)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter
    }
}