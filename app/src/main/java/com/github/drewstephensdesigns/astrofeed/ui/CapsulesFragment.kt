package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drewstephensdesigns.astrofeed.adapters.CapsulesAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentCapsulesBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.utils.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.viewmodels.CapsuleViewModel
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.type.InputRadioButtons

class CapsulesFragment : Fragment() {

    private var _binding : FragmentCapsulesBinding? = null
    private val binding get() = _binding!!

    private lateinit var capsulesAdapter: CapsulesAdapter
    private lateinit var capsuleViewModel: CapsuleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCapsulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        capsuleViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[CapsuleViewModel::class.java]

        capsuleViewModel.capsulesListObjects.observe(viewLifecycleOwner){capsules ->
            binding.loading.visibility = View.VISIBLE

            capsules.let {
                capsulesAdapter = CapsulesAdapter(requireContext())
                binding.capsulesRv.adapter = capsulesAdapter
            }
            when{
                capsules.isEmpty() ->{
                    Config.warningToast(requireContext(), "Nothing loaded, please try again later")

                }
                capsules != null -> {
                    initUI()
                    binding.loading.visibility= View.GONE
                    capsulesAdapter.setCapsuleData(capsules)
                }
            }
        }
    }

    private fun initUI(){
        binding.capsulesRv.layoutManager = LinearLayoutManager(context)
        binding.capsulesRv.setHasFixedSize(true)

        binding.capsulesRv.apply {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                LineDividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                    36
                )
            )
        }


        binding.buttonSorting.setOnClickListener {
            InputSheet().show(requireActivity()){
                title("Sort By")
                with(InputRadioButtons{
                    options(mutableListOf("Capsule Serial Number", "Capsule Type", "Capsule Status"))
                    changeListener { value ->
                        when(value){
                            0->{capsulesAdapter.sortCapsulesBySerial()}
                            1 ->{capsulesAdapter.sortCapsulesByType()}
                            2 ->{capsulesAdapter.sortCapsulesByStatus()}
                        }
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}