package com.example.myapplication.database

import androidx.resourceinspection.annotation.Attribute.IntMap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cartdata")
data class cartuser(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val productname:String,
    val productprice:String,
    val coverimg:String

)
