package com.sen.synk.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sen.synk.R
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.FragmentUserBinding
import com.sen.synk.pref.SharedPreferenceManager
import com.sen.synk.viewmodel.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class UserFragment : Fragment(), EditUserClickListener {

    private val viewModel by viewModels<UserViewModel>()

    private lateinit var binding: FragmentUserBinding

    private var userAdapter = UserRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)

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

    override fun onEditClick(account: Account) {
        Toast.makeText(requireContext(), account.username, Toast.LENGTH_SHORT).show()

        findNavController().navigate(
            UserFragmentDirections.openEditUser(account)
        )
    }

    private fun setupView() {

        binding.rvTest.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
    }

    private fun observeLiveData() {

        viewModel.userLiveData.observe(viewLifecycleOwner) {

            it?.let { it1 -> userAdapter.submitList(it1) }
        }
    }

    private fun logOut() {
        SharedPreferenceManager.clearLoginInfo(requireContext())
        findNavController().navigate(R.id.openLogin)
    }
}