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

import java.net.URI

class categoryadapter(val context: Context, val productList: ArrayList<data>) : RecyclerView.Adapter<categoryadapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoryrecylerlayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]

        // You need to load image into imageView using some image loading library like Glide or Picasso
        // For example, using Glide:
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.img)
        holder.itemView.setOnClickListener {
            val a=Intent(context,categorypage::class.java)
            a.putExtra("cat",productList[position].text)
            context.startActivity(a)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img=itemView.findViewById<ImageView>(R.id.categoryimage)



    }
}
