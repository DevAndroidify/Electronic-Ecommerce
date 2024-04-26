package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.adminapp.adapter.addproduct
import com.example.myapplication.databinding.ActivityCategorypageBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class categorypage : AppCompatActivity() {
    private lateinit var binding:ActivityCategorypageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategorypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list=ArrayList<addproduct>()
        val db = Firebase.firestore
        val a=intent.getStringExtra("cat")
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show()

        db.collection("product").whereEqualTo("productcategory",a!!)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    var productname=document.getString("productname");
                    var productcategory=document.getString("productcategory")
                    var productdescription=document.getString("productdescription")
                    val productmrp=document.getString("productmrp")
                    val productsp=document.getString("productsp")
                    val coverimg=document.getString("coverimg")

                    list.add(addproduct(productname,productdescription,productmrp,productsp,coverimg,productcategory)) // Add data object to the list
                }
                // Set RecyclerView adapter after fetching data

                //var test=ArrayList<data>()
                // test.add(data("apple","https://firebasestorage.googleapis.com/v0/b/ecommerse-e617d.appspot.com/o/category%2Fb9f284e6-dc43-411d-86ab-b621d7cfac63.jpg?alt=media&token=53815af5-0f4d-4c7b-80a6-f61f91019ec7"))
                binding.categoryrecylerview.adapter=eachcategoryadapter(this, list)
                //binding.recylerview.adapter=adapter(requireContext(),test)
                // Notify adapter about the data set change
                //binding.recyclerView.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(this, "Failed to show: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}