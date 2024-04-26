package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface cartdao {
     @Insert
    fun adddata(cartuser: cartuser)

    @Query("SELECT * FROM cartdata ORDER BY ID ASC")
    fun getdata():LiveData<List<cartuser>>

    @Delete
    fun deletedata(cartuser: cartuser)

}