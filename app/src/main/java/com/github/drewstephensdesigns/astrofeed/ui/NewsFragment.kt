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
import com.github.drewstephensdesigns.astrofeed.adapters.NewsAdapter
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentNewsBinding
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.utils.LineDividerItemDecoration
import com.github.drewstephensdesigns.astrofeed.viewmodels.NewsViewModel

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        binding.loading.visibility = View.VISIBLE
        newsViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[NewsViewModel::class.java]

        newsViewModel.newsListObjects.observe(viewLifecycleOwner){newsArticles ->
            newsArticles.let {
                newsAdapter = NewsAdapter(requireContext())
                binding.newsRv.adapter = newsAdapter
            }
            when{
                newsArticles.isEmpty() ->{
                    Config.warningToast(requireContext(), "No Articles Available")
                }
                newsArticles != null ->{
                    initUI()
                    binding.loading.visibility = View.GONE
                    newsAdapter.setNewsArticles(newsArticles)
                }
            }
        }
    }

    private fun initUI(){
        binding.newsRv.layoutManager = LinearLayoutManager(context)
        binding.newsRv.setHasFixedSize(true)

        binding.newsRv.apply {
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