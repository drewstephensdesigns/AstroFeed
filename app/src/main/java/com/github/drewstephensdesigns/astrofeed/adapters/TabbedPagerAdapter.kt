package com.github.drewstephensdesigns.astrofeed.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.drewstephensdesigns.astrofeed.ui.NewsFragment
import com.github.drewstephensdesigns.astrofeed.ui.RedditFragment

class TabbedPagerAdapter(
    activity: FragmentActivity,
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> NewsFragment()
            1 -> RedditFragment()
            // Add more cases if you have more tabs
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}