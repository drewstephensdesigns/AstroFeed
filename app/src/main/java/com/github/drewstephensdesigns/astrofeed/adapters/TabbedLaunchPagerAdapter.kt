package com.github.drewstephensdesigns.astrofeed.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.drewstephensdesigns.astrofeed.ui.PastLaunchesFragment
import com.github.drewstephensdesigns.astrofeed.ui.UpcomingLaunchesFragment

class TabbedLaunchPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> UpcomingLaunchesFragment()
           1 -> PastLaunchesFragment()
           else -> throw IllegalArgumentException("Invalid position")
       }
    }
}