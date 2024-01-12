package com.github.drewstephensdesigns.astrofeed.ui

import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentAboutBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI(){

        binding.textAboutCompany.text = getString(R.string.title_company)
        binding.textAboutDeveloper.text = getString(R.string.title_about)
        binding.textChangeTheme.text = getString(R.string.action_change_theme)


        binding.textAboutCompany.setOnClickListener {
            findNavController().navigate(R.id.navigation_company)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}