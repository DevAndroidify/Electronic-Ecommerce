package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.Observer
import com.example.myapplication.database.database
import com.example.myapplication.databinding.ActivityBuyactivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class buyactivity : AppCompatActivity() {
    private lateinit var binding:ActivityBuyactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBuyactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
            val db=Firebase.firestore

         binding.elevatedButton.setOnClickListener {
             if (!(Firebase.auth.currentUser!!.uid).isEmpty()) {
                  val c=Intent(this,login::class.java)
                 startActivity(c)
             } else {


                 var tollnumber = binding.entertollnumber.editText!!.text.toString()
                 var localaddress = binding.enterlocaladdress.editText!!.text.toString()
                 var phonenumber = binding.enterphonenumber.editText!!.text.toString()
                 if (!tollnumber.isEmpty() && !localaddress.isEmpty() && !phonenumber.isEmpty()) {
                     Toast.makeText(this, "Enter The Details", Toast.LENGTH_SHORT).show()
                 } else {


                     database.getDatabase(this).cartdao().getdata().observe(this, Observer { data ->


                         for (i in data) {
                             var data = hashMapOf(
                                 "productname" to i.productname,
                                 "productprice" to i.productprice,
                                 "coverimg" to i.coverimg,
                                 "uid" to Firebase.auth.currentUser!!.uid
                             )
                             db.collection("cart").add(data).addOnSuccessListener {
                                 Toast.makeText(this, "data added successfully", Toast.LENGTH_SHORT)
                                     .show()
                             }.addOnFailureListener {
                                 Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                             }
                         }
                         var userdata = hashMapOf(
                             "tollnumber" to tollnumber,
                             "localaddress" to localaddress,
                             "phonenumber" to phonenumber
                         )
                         db.collection("buyuser").document(Firebase.auth.currentUser!!.uid)
                             .set(userdata).addOnSuccessListener {

                             }.addOnFailureListener {
                                 Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                             }


                     })
                 }

             }
         }

    }
}