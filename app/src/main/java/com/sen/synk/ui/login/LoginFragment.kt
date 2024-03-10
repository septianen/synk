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
import com.sen.synk.data.constant.LoginStatus
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.FragmentLoginBinding
import com.sen.synk.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: FragmentLoginBinding

    private var username: String? = null
    private var password: String? = null
    private var passwordConfirmation: String? = null
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
        passwordConfirmation = binding.etConfirmPassword.text.toString()

        val userType = if (binding.swAdmin.isSelected) 1 else 2

        account = Account(
            username = username,
            password = password,
            confirmPassword = passwordConfirmation,
            userType = userType
        )
    }

    private fun observeLiveData() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            when(it) {
                LoginStatus.NULL -> showMessage("username dan password harus diisi")
                LoginStatus.FAILED -> showMessage("username atau password salah")
                LoginStatus.USER_NOT_FOUND -> createSnackbar()
                else -> {
                    showMessage("berhasil")
                    openHome()
                }
            }
        }
    }

    private fun showMessage(message: String) {
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
            binding.tilConfirmPassword.visibility = View.GONE
            binding.btnLogin.text = requireContext().resources.getString(R.string.login)
            binding.tvTitle.text = requireContext().resources.getString(R.string.login)
            binding.btnCreateNewAccount.text = requireContext().resources.getString(R.string.create_new_account)
        } else {
            isSignUp = true

            binding.swAdmin.visibility = View.VISIBLE
            binding.tilConfirmPassword.visibility = View.VISIBLE
            binding.btnLogin.text = requireContext().resources.getString(R.string.signup)
            binding.tvTitle.text = requireContext().resources.getString(R.string.signup)
            binding.btnCreateNewAccount.text = requireContext().resources.getString(R.string.already_have_account)
        }
    }

    private fun openHome() {
        findNavController().navigate(R.id.openAlbum)
    }
}