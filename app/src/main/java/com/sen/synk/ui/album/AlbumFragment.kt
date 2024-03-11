package com.sen.synk.ui.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sen.synk.R
import com.sen.synk.databinding.FragmentAlbumBinding
import com.sen.synk.pref.SharedPreferenceManager
import com.sen.synk.viewmodel.album.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private var albumAdapter = AlbumAdapter()

    private val viewModel by viewModels<AlbumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeLiveData()

        binding.btLogout.setOnClickListener {
            logOut()
        }
    }

    private fun setupView() {

        binding.rvTest.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = albumAdapter
        }
    }

    private fun observeLiveData() {

        lifecycleScope.launch {

            viewModel.albumLiveData.observe(viewLifecycleOwner) {

                albumAdapter.submitData(lifecycle, it)
            }
            viewModel.loadingState.collect { isLoading ->
                if (!isLoading){
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun logOut() {
        SharedPreferenceManager.clearLoginInfo(requireContext())
        findNavController().navigate(R.id.openLogin)
    }
}