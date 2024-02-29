package com.example.chatkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var buttonsignup:Button
    private lateinit var buttonlogin:Button
    private lateinit var auth:FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pass:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonsignup = findViewById(R.id.signup)
        buttonlogin = findViewById(R.id.btn)
        email = findViewById(R.id.usernameEt)
        pass = findViewById(R.id.passwordEt)

        auth = FirebaseAuth.getInstance()
        buttonsignup.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"done",Toast.LENGTH_SHORT).show()

        }
        buttonlogin.setOnClickListener {
            val emails = email.text.toString()
            val passds =  pass.text.toString()
            login(emails,passds)
        }

    }

    private fun login(emails: String, passds: String) {
        auth.signInWithEmailAndPassword(emails, passds)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                   val intent = Intent(this,MainActivity3::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext,"Somethingwrong",Toast.LENGTH_SHORT).show()
                }
            }


    }
}