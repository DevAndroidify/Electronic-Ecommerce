package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.cartuser
import com.example.myapplication.database.database
import com.example.myapplication.databinding.FragmentCartBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class cart : Fragment() {

    private lateinit var binding:FragmentCartBinding
    var productname:String?=null
    var productimage:String?=null
    var productprice:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(layoutInflater)


                database.getDatabase(requireContext()).cartdao().getdata().observe(viewLifecycleOwner, Observer { data ->
                    // Update UI or adapter here
                    binding.cartrecylerview.adapter = cartproductadapter(requireContext(), data)
                    var totalprice=0
                    for(product in data){
                        totalprice=totalprice+product.productprice.toInt()
                    }
                    binding.cartcost.text=totalprice.toString()
                })
        binding.Buy.setOnClickListener {
            val a= Intent(requireContext(),buyactivity::class.java)
            startActivity(a)
        }







        // Inflate the layout for this fragment
        return binding.root
    }



}