package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.example.adminapp.adapter.addproduct
import com.example.myapplication.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Locale

class search : Fragment() {
  private var list=ArrayList<addproduct>()
    private lateinit var adapter:searchadapter
  private lateinit var binding:FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentSearchBinding.inflate(layoutInflater)

        val db=Firebase.firestore
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
                binding.searchrecylerview.adapter=searchadapter(requireContext(), list)
                adapter= searchadapter(requireContext(),list)
                //binding.recylerview.adapter=adapter(requireContext(),test)
                // Notify adapter about the data set change
                //binding.recyclerView.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(requireContext(), "Failed to show: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        binding.searchproduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        return binding.root
    }
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<addproduct>()
            for (i in list) {
                if (i.productname!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                binding.searchrecylerview.adapter=searchadapter(requireContext(), filteredList)


            }
        }
    }

}