package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.adminapp.adapter.addproduct
import com.example.myapplication.database.cartuser
import com.example.myapplication.database.database
import com.example.myapplication.databinding.ActivityProductdetailsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class productdetails : AppCompatActivity() {
    private lateinit var binding:ActivityProductdetailsBinding
     var productname:String?=null
    var productsp:String?=null
    var productimage:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val a=intent.getStringExtra("productname")
        val db=Firebase.firestore
        db.collection("product").whereEqualTo("productname",a).limit(1).get().addOnSuccessListener { result->
            val document=result.documents[0]
            productimage=document.getString("coverimg")
            productname=document.getString("productname")
            productsp=document.getString("productsp")
            binding.productdetailstitle.text=document.getString("productname")
            binding.productdetailsprice.text="Rs."+document.getString("productsp")
            binding.productdetailsdescription.text=document.getString("productdescription")
            Glide.with(this).load(document.getString("coverimg")).into(binding.productdetailsimage)


        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

        }
        getproduct()
        binding.productaddtocart.setOnClickListener {
            val database=database.getDatabase(this)
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val user=cartuser(productname=productname!!, productprice = productsp!!, coverimg = productimage!!)
                    database.cartdao().adddata(user)
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@productdetails, "data added successfully", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){

                }

            }

        }

    }
    private fun getproduct() {
        val list=ArrayList<addproduct>()
        val db = com.google.firebase.ktx.Firebase.firestore
        db.collection("product")
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
                binding.productdetailsrecylerview.adapter=productcategory(this,list)
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