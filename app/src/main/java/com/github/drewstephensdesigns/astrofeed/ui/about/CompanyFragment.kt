package com.github.drewstephensdesigns.astrofeed.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentCompanyBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.github.drewstephensdesigns.astrofeed.viewmodels.CompanyViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.transition.MaterialSharedAxis

class CompanyFragment : Fragment() {

    private var _binding: FragmentCompanyBinding? = null
    private val binding get() = _binding!!

    private lateinit var companyViewModel: CompanyViewModel
    private var spaceXLocation = "SpaceX+Rocket+Road+Hawthorne+California"

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
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        binding.loading.visibility = View.VISIBLE


        companyViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CompanyViewModel::class.java]

        companyViewModel.getCompanyInfo().observe(viewLifecycleOwner){companyInfo ->
            if(companyInfo != null){
                binding.loading.visibility = View.GONE
                binding.imageLogo.loadImage(companyInfo.logoUrl)

                // Company Profile
                binding.textFounder.text = resources.getString(R.string.company_founder,
                    companyInfo.administrator?.removePrefix("CEO: ")
                )
                binding.textSummary.text = companyInfo.description
                binding.textFoundedYear.text = companyInfo.foundingYear.toString()
                binding.textLaunchCount.text = companyInfo.totalLaunchCount.toString()
                binding.textSuccess.text = companyInfo.successfulLaunches.toString()
                binding.textLaunchFails.text = companyInfo.failedLaunches.toString()

                binding.textAddress.text = resources.getString(R.string.spacex_pin_location)

                binding.websiteButton.setOnClickListener { openUrl(companyInfo.infoUrl!!) }
                binding.flickrButton.setOnClickListener { openUrl(companyInfo.wikiUrl!!) }
                binding.imageLocationPin.setOnClickListener {
                    // Google Maps expects name of location with spaces replaced with '+'
                    val location = spaceXLocation.replace(" ", "+")
                    val mapUri = Uri.parse("geo:0,0?q=$location")
                    val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                        .apply { setPackage("com.google.android.apps.maps") }
                    mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    context?.applicationContext?.startActivity(mapIntent)
                }
            }
        }

        setButtonProperties(binding.websiteButton, R.string.spacex_website)
        setButtonProperties(binding.flickrButton, R.string.spacex_flickr_website)
    }

    private fun setButtonProperties(button: MaterialButton, textResource: Int) {
        button.text = resources.getString(textResource)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_100))
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.applicationContext?.startActivity(intent)
    }
}