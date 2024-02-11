package com.github.drewstephensdesigns.astrofeed.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentAboutBinding
import com.google.android.material.transition.MaterialSharedAxis

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
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
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI(){
        with(binding){
            textAboutCompany.text = getString(R.string.title_company)
            textAboutDeveloper.text = getString(R.string.title_about)
            textAboutCompany.setOnClickListener { findNavController().navigate(R.id.navigation_company) }
            textAboutDeveloper.setOnClickListener { findNavController().navigate(R.id.navigation_about_dev) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}