package com.example.chatkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    private lateinit var buttonsignup: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var names: EditText
    private lateinit var pass: EditText
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        buttonsignup = findViewById(R.id.btns)
        email = findViewById(R.id.usernameEt)
        names = findViewById(R.id.username)
        pass = findViewById(R.id.passwordEt)
        auth = FirebaseAuth.getInstance()
        buttonsignup.setOnClickListener {
            val emaild =  email.text.toString()
            val name =  names.text.toString()
            val passd = pass.text.toString()
        signup(name,emaild,passd)
        }
        
    }

    private fun signup(name:String,emaild: String, passd: String) {
        auth.createUserWithEmailAndPassword(emaild, passd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,emaild,auth.currentUser?.uid!!)
                    val intent = Intent(this,MainActivity3::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
              Toast.makeText(applicationContext,"Something wrong",Toast.LENGTH_SHORT).show()

                }
            }

    }
//// add Database /////////////////////////////////////////////////////////////////////////
    private fun addUserToDatabase(name:String,emaild:String,uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name,emaild,uid))

    }
}