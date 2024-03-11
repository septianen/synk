package com.sen.synk.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sen.synk.R
import com.sen.synk.data.constant.Message
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.FragmentLoginBinding
import com.sen.synk.pref.SharedPreferenceManager
import com.sen.synk.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: FragmentLoginBinding

    private var username: String? = null
    private var password: String? = null
    private var email: String? = null
    private var account: Account? = null

    private var isSignUp = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()

        binding.btnLogin.setOnClickListener {
            resetError()
            login()
        }

        binding.btnCreateNewAccount.setOnClickListener {
            updateView()
        }
    }

    private fun login() {
        getData()

        if (isSignUp) {
            account?.let {
                viewModel.signup(
                    it
                )
            }
        } else {
            account?.let {
                viewModel.login(
                    it
                )
            }
        }
    }

    private fun getData() {
        username = binding.etUsername.text.toString()
        password = binding.etPassword.text.toString()
        email = binding.etEmail.text.toString()

        val role = if (isSignUp)
            if (binding.swAdmin.isChecked) "Admin" else "User"
        else
            account?.role

        account = Account(
            username = username,
            password = password,
            email = email,
            role = role
        )
    }

    private fun observeLiveData() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {

                    when(it.message) {
                        Message.EMPTY_USERNAME -> binding.tilUsername.error = it.message
                        Message.EMPTY_PASSWORD -> binding.tilPassword.error = it.message
                        Message.PASSWORD_LESS_THAN_8 -> binding.tilPassword.error = it.message
                        Message.INVALID_PASSWORD -> binding.tilPassword.error = it.message
                        Message.EMPTY_EMAIL -> binding.tilEmail.error = it.message
                        Message.INVALID_EMAIL -> binding.tilEmail.error = it.message
                        Message.INVALID_USERNAME_PASSWORD -> {
                            binding.tilUsername.error = it.message
                            binding.tilPassword.error = it.message
                        }
                        Message.USERNAME_NOT_FOUND -> {
                            showMessage(it.message)
                            createSnackbar()
                        }
                        Message.USERNAME_ALREADY_EXIST -> {
                            isSignUp = true
                            updateView()
                        }
                        else -> showMessage(it.message)
                    }
                }
                is Resource.Success -> {
                    this.account = it.data
                    showMessage("Welcome")
                    SharedPreferenceManager.saveLoginInfo(requireContext(), it.data?.username!!)
                    openHome()
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun createSnackbar() {
        Snackbar.make(requireView(), "username tidak ditemukan", Snackbar.LENGTH_LONG)
            .setAction("Registrasi") {
                updateView()
            }.show()
    }

    private fun updateView() {

        if (isSignUp) {
            isSignUp = false

            binding.swAdmin.visibility = View.GONE
            binding.tilEmail.visibility = View.GONE
            binding.btnLogin.text = requireContext().resources.getString(R.string.login)
            binding.tvTitle.text = requireContext().resources.getString(R.string.login)
            binding.btnCreateNewAccount.text = requireContext().resources.getString(R.string.create_new_account)
        } else {
            isSignUp = true

            binding.swAdmin.visibility = View.VISIBLE
            binding.tilEmail.visibility = View.VISIBLE
            binding.btnLogin.text = requireContext().resources.getString(R.string.signup)
            binding.tvTitle.text = requireContext().resources.getString(R.string.signup)
            binding.btnCreateNewAccount.text = requireContext().resources.getString(R.string.already_have_account)
        }
    }

    private fun openHome() {
        if (account?.role.equals("Admin"))
            findNavController().navigate(R.id.openUser)
        else
            findNavController().navigate(R.id.openAlbum)
    }

    private fun resetError() {
        binding.tilUsername.isErrorEnabled = false
        binding.tilPassword.isErrorEnabled = false
        binding.tilEmail.isErrorEnabled = false
    }
}