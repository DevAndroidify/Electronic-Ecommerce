package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navhost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController=navhost!!.findNavController()
        val popupMenu=PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottomnavmenu)
        binding.bottomNavigationView.setupWithNavController(navController);



    }
}