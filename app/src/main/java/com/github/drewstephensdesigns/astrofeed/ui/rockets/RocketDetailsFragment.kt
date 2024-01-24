package com.github.drewstephensdesigns.astrofeed.ui.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.drewstephensdesigns.astrofeed.MainActivity
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentRocketDetailsBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config.loadImage

class RocketDetailsFragment : Fragment() {

    private val rocketArgs: RocketDetailsFragmentArgs by navArgs()

    private var _binding: FragmentRocketDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRocketDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        with(binding){

            // Loads random image from Flickr source
            imageRocket.loadImage(rocketArgs.rocketImage)

            textRocketName.text = rocketArgs.rocketType
            textRocketBoosterTotal.text = rocketArgs.rocketBoosters.toString()

            val status = when(rocketArgs.rocketStatus){
                true -> "Active"
                false -> "Not Active"
            }
            textCapsuleStatus.text = status

            // Changes true/false active status to actual depiction
            // Starship is still in development / Falcon 1 cancelled
            if(rocketArgs.rocketType == "Starship"){ textCapsuleStatus.text = context?.getString(R.string.text_status_development) }
            else if (rocketArgs.rocketType == "Falcon 1"){ textCapsuleStatus.text = context?.getString(R.string.text_status_cancelled) }

            textCapsuleLaunch.text = rocketArgs.rocketFirstFlight
            textCapsuleLandings.text = rocketArgs.rocketSuccessRate.toString()
            textCapsuleReused.text = rocketArgs.rocketEngineType
            textRocketPropellant1.text = rocketArgs.rocketPropellant1
            textRocketPropellant2.text = rocketArgs.rocketPropellant2
            textRocketThrust.text = getString(R.string.rocket_thrust, rocketArgs.rocketThrust.toString())
            textRocketCost.text = getString(R.string.rocket_cost, rocketArgs.rocketCost.toString())
            textRocketStages.text = rocketArgs.rocketStages.toString()

            // Sets Toolbar Title to Rocket Name
            (requireActivity() as AppCompatActivity).supportActionBar?.title = rocketArgs.rocketType
        }
    }
}