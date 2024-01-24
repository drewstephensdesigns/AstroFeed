package com.github.drewstephensdesigns.astrofeed.ui.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drewstephensdesigns.astrofeed.adapters.rockets.RocketAdapter
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentRocketsBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.ui.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.viewmodels.RocketViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlin.random.Random

class RocketsFragment : Fragment(), RocketAdapter.RocketClickListener {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var rocketAdapter: RocketAdapter
    private lateinit var rocketViewModel: RocketViewModel

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
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initViewModel()
    }

    private fun initViewModel(){
        rocketViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[RocketViewModel::class.java]

        rocketViewModel.rocketListObjects.observe(viewLifecycleOwner){rockets ->
            binding.loading.visibility = View.VISIBLE

            rockets.let {
                rocketAdapter = RocketAdapter(requireContext(), this)
                binding.rocketRv.adapter = rocketAdapter
            }
            when{
                rockets.isEmpty() ->{
                    //TODO("Change to error toast")
                    Config.successToast(requireContext(), "Nothing Here...")
                }
                rockets != null -> {
                    initUI()
                    binding.loading.visibility = View.GONE
                    rocketAdapter.setRocketData(rockets)
                }
            }
        }
    }

    private fun initUI(){
        binding.rocketRv.layoutManager = LinearLayoutManager(context)
        binding.rocketRv.setHasFixedSize(true)

        binding.rocketRv.apply {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                LineDividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                    36
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRocketItemClick(rocket: Rocket) {
        val myImageList = intArrayOf(rocket.flickrImages.size)
        val randomNumber = Random.nextInt(myImageList.size)

        val action =  RocketsFragmentDirections.actionRocketDetailDest(
            rocket.flickrImages[randomNumber],
            rocket.name!!,
            rocket.boosters!!,
            rocket.active!!,
            rocket.firstFlight!!,
            rocket.description!!,
            rocket.wikipedia!!,
            rocket.successRatePct!!,
            rocket.engines?.type!!,
            rocket.engines?.propellant1!!,
            rocket.engines?.propellant2!!,
            rocket.engines?.thrustSeaLevel?.kN!!,
            rocket.costPerLaunch!!,
            rocket.stages!!
        )

        navController.navigate(action)
    }
}