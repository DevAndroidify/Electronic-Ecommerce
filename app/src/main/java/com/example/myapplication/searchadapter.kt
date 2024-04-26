package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminapp.adapter.addproduct

import java.net.URI

class searchadapter( val context: Context,var productList: ArrayList<addproduct>) : RecyclerView.Adapter<searchadapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eachcategory, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]
        holder.txt.text=currentItem.productname
        holder.price.text=currentItem.productsp
        // You need to load image into imageView using some image loading library like Glide or Picasso
        // For example, using Glide:
        Glide.with(holder.itemView.context).load(currentItem.coverimages).into(holder.img)
        holder.itemView.setOnClickListener {
            val a=Intent(context,productdetails::class.java)
            a.putExtra("productname",currentItem.productname)
            context.startActivity(a)
        }
    }
    fun setFilteredList(productList: ArrayList<addproduct>){
        this.productList=productList
        Toast.makeText(context, "y", Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img=itemView.findViewById<ImageView>(R.id.eachcategoryimage)
        val txt=itemView.findViewById<TextView>(R.id.eachcategoryname)

        val price=itemView.findViewById<TextView>(R.id.eachcategoryprice)


    }
}
