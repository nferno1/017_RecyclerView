package com.skillbox.a017_recyclerview.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.skillbox.a017_recyclerview.R
import com.skillbox.a017_recyclerview.databinding.FragmentMovieListBinding
import com.skillbox.a017_recyclerview.models.Movie
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModels()

    private val movieAdapter = MovieAdapter()
    private val moviewListAdapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recycler.adapter = moviewListAdapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.movies.onEach {
//            movieAdapter.setData(it)
            moviewListAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.isLoading.onEach {
            binding.swipeRefresh.isRefreshing = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
