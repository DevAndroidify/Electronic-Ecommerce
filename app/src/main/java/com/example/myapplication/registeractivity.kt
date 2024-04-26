package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegisteractivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class registeractivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username:String
    private lateinit var binding:ActivityRegisteractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisteractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth
        binding.continueasguest.setOnClickListener {

            val a=Intent(this,MainActivity::class.java)
            startActivity(a)
            finish()
        }
       binding.loginacti.setOnClickListener {
           val x=Intent(this,login::class.java)
           startActivity(x)
           finish()
       }
        binding.submit.setOnClickListener {
            var email=binding.email.editText!!.text.toString()
            var password=binding.password.editText!!.text.toString()
            if(email.isEmpty() && password.isEmpty() ){
                Toast.makeText(this, "Enter The Details", Toast.LENGTH_SHORT).show()
            }else {


                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val db=Firebase.firestore
         username=binding.username.editText!!.text.toString()

        val data= hashMapOf(
            "email" to user!!.email,
            "username" to username,
            "uid" to user.uid

        )
        db.collection("users").add(data).addOnSuccessListener {
            val a=Intent(this,MainActivity::class.java)
            startActivity(a)
            finish()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val a=Intent(this,MainActivity::class.java)
            startActivity(a)
            finish()
        }
    }
}