package com.github.drewstephensdesigns.astrofeed.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.adapters.launches.TabbedLaunchPagerAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.TabbedLaunchFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis

class TabbedLaunchFragment : Fragment() {

    private var _binding : TabbedLaunchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabbedLaunchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val launchTabLayout = binding.launchTabLayout
        val launchViewPager = binding.launchViewPager

        val adapter = TabbedLaunchPagerAdapter(requireActivity())
        launchViewPager.adapter = adapter

        val tabIcons = intArrayOf(R.drawable.ic_current, R.drawable.ic_history)
        val titles = arrayListOf("Upcoming", "Past")

        TabLayoutMediator(launchTabLayout, launchViewPager){tab, position ->
            tab.setIcon(tabIcons[position])
            tab.text = titles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}