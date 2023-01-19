package com.example.bolovai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bolovai.adapters.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity2 : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager2Adapter: ViewPagerAdapter
    private val NavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener {
        when (it.itemId){
            R.id.homeFragment ->{
                viewPager2.currentItem = 0
                return@OnItemSelectedListener true
            }
            R.id.profileFragment ->{
                viewPager2.currentItem = 1
                return@OnItemSelectedListener true
            }

        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        bottomNavigationView=findViewById(R.id.bottomNavView)
        viewPager2 = findViewById(R.id.view_pager)
        viewPager2Adapter = ViewPagerAdapter(this)
        viewPager2.adapter = viewPager2Adapter

        bottomNavigationView.setOnItemSelectedListener (NavigationItemSelectedListener)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0->bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked=true
                    1->bottomNavigationView.menu.findItem(R.id.profileFragment).isChecked=true

                }
            }
        })

    }
}