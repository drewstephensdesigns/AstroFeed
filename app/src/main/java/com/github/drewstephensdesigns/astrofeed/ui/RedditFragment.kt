package com.github.drewstephensdesigns.astrofeed.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drewstephensdesigns.astrofeed.adapters.RedditPostAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentRedditBinding
import com.github.drewstephensdesigns.astrofeed.utils.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.utils.openWebLink
import com.github.drewstephensdesigns.astrofeed.viewmodels.RedditViewModel

class RedditFragment: Fragment(){

    private var _binding: FragmentRedditBinding? = null
    private val binding get() = _binding!!

    private lateinit var redditPostAdapter: RedditPostAdapter
    private lateinit var redditViewModel: RedditViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRedditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

    }

    private fun initViewModel(){
        binding.loading.visibility = View.VISIBLE
        redditViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[RedditViewModel::class.java]


        redditViewModel.redditListObjects.observe(viewLifecycleOwner){redditPosts ->
            redditPosts.let {
               redditPostAdapter = RedditPostAdapter(requireContext()) { openWebLink(it) }
                binding.redditRv.adapter = redditPostAdapter
            }

            when{
                redditPosts.isEmpty() ->{
                    binding.loading.visibility = View.GONE
                    binding.noResultsFound.visibility = View.VISIBLE
                }
                redditPosts != null ->{
                    Log.i("REDDIT POSTS", redditPosts[5].permalink)
                    initUI()
                    binding.noResultsFound.visibility = View.GONE
                    binding.loading.visibility = View.GONE
                    redditPostAdapter.setRedditPosts(redditPosts)
                }
            }
        }
    }

    private fun initUI(){
        binding.redditRv.layoutManager = LinearLayoutManager(context)

        binding.redditRv.apply {
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
}