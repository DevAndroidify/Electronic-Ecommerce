package com.example.myapplication

import android.icu.lang.UCharacter.BidiPairedBracketType.CLOSE
import android.icu.lang.UCharacter.BidiPairedBracketType.OPEN
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.adminapp.adapter.addproduct
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class home : Fragment() {
      private lateinit var binding:FragmentHomeBinding
        private lateinit var toggle:ActionBarDrawerToggle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentHomeBinding.inflate(layoutInflater)
        val imageList = ArrayList<SlideModel>() // Create image list
          toggle= ActionBarDrawerToggle(requireActivity(),binding.drawerlayout,OPEN,CLOSE)
        binding.drawerlayout.addDrawerListener(toggle)
        binding.search.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                view.findNavController().navigate(R.id.action_home2_to_search)
                return@setOnTouchListener true
            }
            false
        }

        binding.navview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.cart->{
                    findNavController().navigate(R.id.cart)

                true
                    }
                    R.id.more->{
                        findNavController().navigate(R.id.more)

                  true}

            }
            true

        }


        imageList.add(SlideModel(R.drawable.sales))
        imageList.add(SlideModel(R.drawable.sales3))
        imageList.add(SlideModel(R.drawable.sales2))

        binding.cardView.setImageList(imageList)

         getdata()
        getproduct()

        return binding.root
    }

    private fun getproduct() {
       val list=ArrayList<addproduct>()
        val db = Firebase.firestore
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
                binding.productrecylerview.adapter=productcategory(requireContext(),list)
                //binding.recylerview.adapter=adapter(requireContext(),test)
                // Notify adapter about the data set change
                //binding.recyclerView.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(requireContext(), "Failed to show: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getdata() {
        val list = ArrayList<data>()
        val db = Firebase.firestore
        db.collection("category")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    var text=document.getString("cate");
                    var img=document.getString("img")

                    list.add(data(text,img)) // Add data object to the list
                }
                // Set RecyclerView adapter after fetching data

                //var test=ArrayList<data>()
                // test.add(data("apple","https://firebasestorage.googleapis.com/v0/b/ecommerse-e617d.appspot.com/o/category%2Fb9f284e6-dc43-411d-86ab-b621d7cfac63.jpg?alt=media&token=53815af5-0f4d-4c7b-80a6-f61f91019ec7"))
                binding.recyclerView.adapter=categoryadapter(requireContext(),list)
                //binding.recylerview.adapter=adapter(requireContext(),test)
                // Notify adapter about the data set change
                //binding.recyclerView.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(requireContext(), "Failed to show: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}