package com.github.drewstephensdesigns.astrofeed.ui

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
import com.github.drewstephensdesigns.astrofeed.adapters.UpcomingLaunchesAdapter
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentUpcomingLaunchesBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.utils.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.viewmodels.UpcomingLaunchesViewModel

class UpcomingLaunchesFragment : Fragment(), UpcomingLaunchesAdapter.LaunchClickListener {

    private var _binding: FragmentUpcomingLaunchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var launchesAdapter: UpcomingLaunchesAdapter
    private lateinit var launchesViewModel: UpcomingLaunchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        navController = findNavController()
    }

    private fun initViewModel(){
        binding.loading.visibility = View.VISIBLE
        launchesViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[UpcomingLaunchesViewModel::class.java]

        launchesViewModel.upcomingLaunches.observe(viewLifecycleOwner){launches ->
            launches.let {
                launchesAdapter = UpcomingLaunchesAdapter(requireContext(), this)
                binding.launchRv.adapter = launchesAdapter
            }

            when{
                launches.isEmpty() ->{
                    Config.warningToast(requireContext(), "No launches")
                }
                launches != null ->{
                    initUI()
                    binding.loading.visibility = View.GONE
                    launchesAdapter.setUpcomingLaunches(launches)
                }
            }
        }
    }

    private fun initUI(){
        binding.launchRv.layoutManager = LinearLayoutManager(context)
        binding.launchRv.setHasFixedSize(true)

        binding.launchRv.apply {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                LineDividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                    0
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLaunchItemClick(launch: LaunchResponse) {

        val action = TabbedLaunchFragmentDirections.actionLaunchDetailDest(
            launch.missionPatches?.firstOrNull()?.imageUrl,
            launch.mission?.name,
            launch.mission?.description,
            launch.status?.name,
            launch.status?.description,
            launch.pad?.name,
            launch.pad?.mapImage,
            launch.pad?.mapUrl,
            launch.net!!,
            launch.mission?.type,
            launch.mission?.orbit?.name,
            launch.pad?.location?.name

        )
        navController.navigate(action)
    }
}