package com.example.bolovai.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bolovai.fragments.HomeFragment
import com.example.bolovai.fragments.ProfileFragment

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->HomeFragment()
            1->ProfileFragment()
            else -> HomeFragment()
        }
    }

}