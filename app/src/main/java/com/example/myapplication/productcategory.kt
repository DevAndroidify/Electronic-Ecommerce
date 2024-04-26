package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminapp.adapter.addproduct

import java.net.URI

class productcategory( val context: Context,val productList: ArrayList<addproduct>) : RecyclerView.Adapter<productcategory.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.productrecylerlayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]
        holder.productname.text=currentItem.productname
        // You need to load image into imageView using some image loading library like Glide or Picasso
        // For example, using Glide:
        Glide.with(holder.itemView.context).load(currentItem.coverimages).into(holder.coverimg)
        holder.itemView.setOnClickListener {
            val a=Intent(context,productdetails::class.java)
            a.putExtra("productname",currentItem.productname)
            context.startActivity(a)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productname=itemView.findViewById<TextView>(R.id.productname)
       var coverimg=itemView.findViewById<ImageView>(R.id.coverimage)


    }
}
