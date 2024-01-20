package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.drewstephensdesigns.astrofeed.MainActivity
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentLaunchDetailsBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage
import com.github.drewstephensdesigns.astrofeed.utils.openWebLink
import com.google.android.material.transition.MaterialSharedAxis

class LaunchDetailsFragment : Fragment() {

    private val launchArgs: LaunchDetailsFragmentArgs by navArgs()

    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

    private val upcoming : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLaunchDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        with(binding){

            // Mission Patch
            patchIv.loadImage(launchArgs.patch)

            if (launchArgs.patch.isNullOrEmpty()){
                patchIv.setImageResource(R.drawable.mission_patch)
            }

            if(launchArgs.missionName == "PACE (Plankton, Aerosol, Cloud, ocean Ecosystem)"){
                patchIv.loadImage("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/logo/national2520aeronautics2520and2520space2520administration_logo_20190207032448.png")
            }

            // Countdown

            //textLaunchCountdown.text = launchArgs.s

           // Mission Name/Details
            textLaunchMission.text = launchArgs.missionName
            textLaunchMissionDetails.text = launchArgs.missionDescription

           // Launch Status/Description
            textLaunchMissionStatusName.text = launchArgs.launchStatusName
            textLaunchMissionStatusDescrip.text = launchArgs.launchStatusDescription

            // Launch Site
            textLaunchMissionLaunchSite.text = launchArgs.launchSiteName

            // Map image (clicking open up Google Maps location)
            mapIv.loadImage(launchArgs.launchSiteMapImage)
            mapIv.setOnClickListener {
                openWebLink(launchArgs.launchSiteMapURL!!)
            }
        }
    }
}