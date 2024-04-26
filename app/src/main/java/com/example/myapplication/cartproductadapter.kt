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
import com.example.myapplication.database.cartuser
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import java.net.URI

class cartproductadapter( val context: Context,val productList: List<cartuser>) : RecyclerView.Adapter<cartproductadapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eachcategorycart, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]
        holder.txt.text=currentItem.productname
        holder.price.text=currentItem.productprice
        // You need to load image into imageView using some image loading library like Glide or Picasso
        // For example, using Glide:
        Glide.with(holder.itemView.context).load(currentItem.coverimg).into(holder.img)
          holder.deletebutton.setOnClickListener {
              var database=database.getDatabase(context)
              GlobalScope.launch(Dispatchers.IO) {
                  try {
                      database.cartdao().deletedata(cartuser(id=currentItem.id,"","",""))

                  }catch (e:Exception){

                  }
              }
          }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img=itemView.findViewById<ImageView>(R.id.eachcategoryimage)
        val txt=itemView.findViewById<TextView>(R.id.eachcategoryname)
       val deletebutton=itemView.findViewById<ImageView>(R.id.deletebutton)
        val price=itemView.findViewById<TextView>(R.id.eachcategoryprice)


    }
}
