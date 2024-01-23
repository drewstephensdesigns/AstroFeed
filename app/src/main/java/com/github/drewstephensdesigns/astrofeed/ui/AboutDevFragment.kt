package com.github.drewstephensdesigns.astrofeed.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentAboutDevBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.info.InfoSheet

class AboutDevFragment : Fragment() {

    private var _binding : FragmentAboutDevBinding? = null
    private val binding get() = _binding!!

    private val deviceInfoText = "Manufacturer: ${Build.MANUFACTURER}\n" +
            "Model: ${Build.MODEL}\n" +
            "SDK: ${Build.VERSION.SDK_INT}\n" +
            "Board: ${Build.BOARD}\n" +
            "OS: Android ${Build.VERSION.RELEASE}\n" +
            "Arch: ${Build.SUPPORTED_ABIS[0]}\n" +
            "Product: ${Build.PRODUCT}"

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
        _binding = FragmentAboutDevBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        // Gets version info from Gradle/Manifest
        val versionHeader = resources.getString(R.string.version_header)
        val versionName = context?.packageManager?.getPackageInfo(context?.packageName!!, 0)?.versionName
        val formattedVersionText = getString(R.string.version_text, versionHeader, versionName)

        with(binding){
            appIv.setImageResource(R.mipmap.ic_launcher)
            textAppName.text = resources.getString(R.string.app_name)
            textAppVersion.text = formattedVersionText
            textAppPowered.text = resources.getString(R.string.powered_by)

            // App Rating - Disabled until launched on Google Play
            rate.visibility = View.GONE

            // Social Media Links
            setupCard(linkedin, resources.getString(R.string.developer_linkedin_url))
            setupCard(github, resources.getString(R.string.developer_github_url))
            setupCard(instagram, resources.getString(R.string.developer_instagram_url))

            // Feedback
            feedback.setOnClickListener { sendFeedback() }

            // Shows user device info
            device.setOnClickListener { showDeviceInfo() }

            // Long click to save device info to clipboard
            device.setOnLongClickListener {Config.save(requireContext(), deviceInfoText)
                true
            }

            // Open Source License Plugin
            licenses.setOnClickListener {
                startActivity(Intent( requireActivity(), OssLicensesMenuActivity::class.java))
                OssLicensesMenuActivity.setActivityTitle(getString(com.google.android.gms.oss.licenses.R.string.oss_license_title))
            }
        }
    }
    private fun setupCard(card: MaterialCardView, link: String) {
        card.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = (Uri.parse(link))
            startActivity(intent)
        }
        card.setOnLongClickListener {
            onLongClick(link)
            true
        }
    }

    // Long Press to Copy href links to clipboard
    private fun onLongClick(href: String) {
        // copy the link to the clipboard
        Config.save(requireContext(), href)
        // show the snackBar with open action
        Snackbar.make(
            binding.root,
            R.string.copied_to_clipboard,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.open_copied) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse(href))
                startActivity(intent)
            }
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .show()
    }

    private fun sendFeedback(){
        val versionName = context?.packageManager?.getPackageInfo(context?.packageName!!, 0)?.versionName
        val send = Intent(Intent.ACTION_SENDTO)

        // Email subject (App Name and Version Code for troubleshooting)
        val uriText = "mailto:" + Uri.encode("drewstephensdesigns@gmail.com") +
                "?subject=" + Uri.encode("App Feedback: ") + resources.getString(R.string.app_name) + " " + versionName
        val uri = Uri.parse(uriText)
        send.data = uri

        // Displays apps that are able to handle email
        startActivity(Intent.createChooser(send, "Send feedback..."))
    }

    // Displays users device info
    private fun showDeviceInfo(){
        InfoSheet().show(requireContext()){
            style(SheetStyle.DIALOG)
            title("Device Info")
            content(deviceInfoText)
            onPositive("Ok")
        }
    }
}