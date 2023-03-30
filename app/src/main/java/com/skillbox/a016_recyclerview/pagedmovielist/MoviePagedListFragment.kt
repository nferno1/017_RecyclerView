package com.skillbox.a017_recyclerview.pagedmovielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.skillbox.a017_recyclerview.R
import com.skillbox.a017_recyclerview.databinding.FragmentMoviePagedListBinding
import com.skillbox.a017_recyclerview.models.Movie
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MoviePagedListFragment : Fragment() {
    private var _binding: FragmentMoviePagedListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviePagedListViewModel by viewModels()

    private val pagedAdapter = MoviePagedListAdapter { movie -> onItemClick(movie) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePagedListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = pagedAdapter.withLoadStateFooter(MyLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener {
            pagedAdapter.refresh()
        }

        pagedAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagedMovies.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(item: Movie) {
        findNavController().navigate(R.id.SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
