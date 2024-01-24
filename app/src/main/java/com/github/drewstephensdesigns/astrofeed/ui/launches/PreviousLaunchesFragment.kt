package com.github.drewstephensdesigns.astrofeed.ui.launches

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
import com.github.drewstephensdesigns.astrofeed.adapters.launches.PreviousLaunchesAdapter
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentPreviousLaunchesBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.ui.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.viewmodels.PreviousLaunchesViewModel

class PreviousLaunchesFragment : Fragment(), PreviousLaunchesAdapter.LaunchClickListener {

    private var _binding: FragmentPreviousLaunchesBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var previousLaunchesAdapter: PreviousLaunchesAdapter
    private lateinit var previousLaunchesViewModel: PreviousLaunchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviousLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        navController = findNavController()
    }

    private fun initViewModel(){
        binding.loading.visibility = View.VISIBLE
        previousLaunchesViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory
                    .getInstance(requireActivity().application)
            )[PreviousLaunchesViewModel::class.java]

        previousLaunchesViewModel.previousLaunches.observe(viewLifecycleOwner){previousLaunches ->
            previousLaunches.let {
                previousLaunchesAdapter = PreviousLaunchesAdapter(requireContext(), this)
                binding.previousLaunchRv.adapter = previousLaunchesAdapter
            }

            when{
                //TODO("Add Lottie Animation")
                previousLaunches.isEmpty() ->{
                    Config.warningToast(requireContext(), "No Launches")
                }
                previousLaunches != null ->{
                    initUI()
                    binding.loading.visibility = View.GONE
                    previousLaunchesAdapter.setPreviousLaunches(previousLaunches)
                }
            }
        }
    }

    private fun initUI(){
        binding.previousLaunchRv.layoutManager = LinearLayoutManager(context)
        binding.previousLaunchRv.setHasFixedSize(true)

        binding.previousLaunchRv.apply {
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
        Config.infoToast(requireContext(), "Featured not yet implemented")
    }
}