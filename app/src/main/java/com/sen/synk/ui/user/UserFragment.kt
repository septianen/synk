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
import com.sen.synk.data.constant.Message
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.FragmentUserBinding
import com.sen.synk.pref.SharedPreferenceManager
import com.sen.synk.ui.enterpassword.PasswordBottomSheetFragment
import com.sen.synk.viewmodel.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class UserFragment : Fragment(), EditUserClickListener {

    private val viewModel by viewModels<UserViewModel>()
    private val bottomSheetFragment = PasswordBottomSheetFragment()

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

        findNavController().navigate(
            UserFragmentDirections.openEditUser(account)
        )
    }

    override fun onDeleteClick(account: Account) {
        bottomSheetFragment.setBottomSheetListener(object : PasswordBottomSheetFragment.BottomSheetListener {
            override fun onTextEntered(text: String) {
                viewModel.deleteAccount(account, text)
            }
        })

        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupView() {

        binding.rvTest.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
    }

    private fun observeLiveData() {

        viewModel.userLiveData.observe(viewLifecycleOwner) {

            when(it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> userAdapter.submitList(it1) }

                    if (bottomSheetFragment.isVisible)
                        bottomSheetFragment.dismiss()
                }
                is Resource.Error -> {
                    when(it.message) {
                        Message.EMPTY_PASSWORD, Message.FALSE_PASSWORD -> bottomSheetFragment.binding.tilPassword.error = it.message
                    }
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun logOut() {
        SharedPreferenceManager.clearLoginInfo(requireContext())
        findNavController().navigate(R.id.openLogin)
    }
}