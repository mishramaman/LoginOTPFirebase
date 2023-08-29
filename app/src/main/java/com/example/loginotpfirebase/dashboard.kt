package com.example.loginotpfirebase

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class dashboard : AppCompatActivity() {
    lateinit var button3:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        button3=findViewById(R.id.btn3)
        button3.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }
    }

}