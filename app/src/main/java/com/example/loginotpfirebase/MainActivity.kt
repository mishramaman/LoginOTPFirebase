package com.example.loginotpfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker

class MainActivity : AppCompatActivity() {
    lateinit var ccp: CountryCodePicker
    lateinit var button: Button
    lateinit var edt1:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ccp=findViewById(R.id.ccp)
        button=findViewById(R.id.btn1)
        edt1=findViewById(R.id.editText)
        ccp.registerCarrierNumberEditText(edt1)

        button.setOnClickListener {
            val intent=Intent(this,ManageOTP::class.java)
            intent.putExtra("mobile",ccp.fullNumberWithPlus.replace(" ",""))
            startActivity(intent)
        }

    }
}