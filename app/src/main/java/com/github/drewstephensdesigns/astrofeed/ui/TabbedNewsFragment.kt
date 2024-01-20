package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.adapters.TabbedNewsPagerAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.TabbedNewsFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis


class TabbedNewsFragment : Fragment() {

    private var _binding : TabbedNewsFragmentBinding? = null
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
        _binding = TabbedNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        // Setup ViewPager2 with an adapter (you need to create this adapter)
        val adapter = TabbedNewsPagerAdapter(requireActivity())
        viewPager.adapter = adapter


        val tabIcons = intArrayOf(R.drawable.ic_news_article, R.drawable.snoo)
        val titles = arrayListOf("News", "Reddit Feed")

        // Connect TabLayout and ViewPager2 using TabLayoutMediator
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(tabIcons[position])
            tab.text = titles[position]

        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}