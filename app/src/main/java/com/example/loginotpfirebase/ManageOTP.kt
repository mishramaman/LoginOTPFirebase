package com.example.loginotpfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ManageOTP : AppCompatActivity() {

    lateinit var edt2:EditText
    lateinit var button2:Button
    lateinit var phoneNumber:String
    lateinit var auth:FirebaseAuth
    lateinit var otpId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_otp)

        auth=FirebaseAuth.getInstance()
        phoneNumber=intent.getStringExtra("mobile").toString()
        edt2=findViewById(R.id.text2)
        button2=findViewById(R.id.btn2)

        initiateOtp()

        button2.setOnClickListener {
            if(edt2.text.toString().isEmpty())
                Toast.makeText(this,"Blank field cannot be processed",Toast.LENGTH_SHORT).show()
            else if(edt2.text.toString().length!=6)
                Toast.makeText(this,"Invalid Otp",Toast.LENGTH_SHORT).show()
            else {
                var credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(otpId,edt2.text.toString())
                signInWithPhoneAuthCredential(credential)
            }
        }



    }
    fun initiateOtp(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            if (e is FirebaseAuthInvalidCredentialsException) {
            } else if (e is FirebaseTooManyRequestsException) {
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
            }
        }

        override fun onCodeSent(
            verificationId: String,

            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            otpId=verificationId
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val intent=Intent(this,dashboard::class.java)
                    startActivity(intent)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,"sign in code error",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}