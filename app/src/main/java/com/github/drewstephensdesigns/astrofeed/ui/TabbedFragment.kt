package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.adapters.TabbedPagerAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.TabbedFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator


class TabbedFragment : Fragment() {

    private var _binding : TabbedFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabbedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        // Setup ViewPager2 with an adapter (you need to create this adapter)
        val adapter = TabbedPagerAdapter(
            requireActivity()
        )
        viewPager.adapter = adapter

        val tabIcons = intArrayOf(R.drawable.ic_news_article, R.drawable.snoo)
        val titles = arrayListOf("News", "Reddit Feed")

        // Connect TabLayout and ViewPager2 using TabLayoutMediator
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            tab.setIcon(tabIcons[position]);
            tab.text = titles[position]

        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}