package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.os.CountDownTimer
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class LaunchDetailsFragment : Fragment() {

    private val launchArgs: LaunchDetailsFragmentArgs by navArgs()

    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

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

        val launchTimeStr = launchArgs.launchTime
        val launchTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(launchTimeStr!!)

        // Call a method to start the countdown
        startCountdown(launchTime!!)
    }

    private fun startCountdown(launchTime: Date) {

        // Convert GMT time to local time
        val localTimeZone = TimeZone.getDefault()
        val gmtTimeZone = TimeZone.getTimeZone("GMT")
        val offset = localTimeZone.rawOffset - gmtTimeZone.rawOffset
        val launchTimeLocal = Date(launchTime.time + offset)


        object : CountDownTimer(launchTimeLocal.time - System.currentTimeMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val countdownText = String.format(
                    Locale.getDefault(),
                    "T - %02d days %02d:%02d:%02d",
                    days,
                    hours,
                    minutes,
                    seconds
                )

                // Update UI with countdown (e.g., set a TextView)
                binding.textLaunchCountdown.text = countdownText
            }

            override fun onFinish() {
                // Handle countdown finish if needed
                //binding.textLaunchCountdown.text = context?.resources?.getString(R.string.title_launch_go)
            }
        }.start()
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

           // Mission Name/Details
            textLaunchMission.text = launchArgs.missionName
            textLaunchMissionDetails.text = launchArgs.missionDescription

            textLaunchMissionType.text = context?.resources?.getString(R.string.mission_type, launchArgs.missionType)
            textLaunchMissionOrbit.text = context?.resources?.getString(R.string.orbit_type, launchArgs.missionOrbit)

           // Launch Status/Description
            textLaunchMissionStatusName.text = launchArgs.launchStatusName
            textLaunchMissionStatusDescrip.text = launchArgs.launchStatusDescription

            // Launch Site
            textLaunchMissionLaunchSite.text = launchArgs.launchSiteName
            textLaunchMissionLaunchLocation.text = launchArgs.launchPadLocation

            // Map image (clicking open up Google Maps location)
            mapIv.loadImage(launchArgs.launchSiteMapImage)
            mapIv.setOnClickListener {
                openWebLink(launchArgs.launchSiteMapURL!!)
            }
        }
    }
}