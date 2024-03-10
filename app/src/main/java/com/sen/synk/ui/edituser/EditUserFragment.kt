package com.sen.synk.ui.edituser

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sen.synk.R
import com.sen.synk.data.constant.ARGS
import com.sen.synk.data.constant.Resource
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.FragmentEditUserBinding
import com.sen.synk.viewmodel.edituser.EditUserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [EditUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class EditUserFragment : Fragment() {

    private val viewModel by viewModels<EditUserViewModel>()

    private lateinit var binding: FragmentEditUserBinding

    private var account: Account? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            account = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(ARGS.ACCOUNT, Account::class.java)
            } else {
                it.getSerializable(ARGS.ACCOUNT) as Account
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeLiveData()

        binding.btnBack.setOnClickListener {
            openHome()
        }

        binding.btnSave.setOnClickListener {
            getData()
            account?.let { it1 -> viewModel.saveData(it1) }
        }
    }

    private fun setupView() {
        if (account == null)
            return

        binding.tvTitle.text = account?.username
        binding.etUsername.setText(account?.username)
        binding.etPassword.setText(account?.password)
        binding.etEmail.setText(account?.email)
        binding.etRole.setText(account?.role)

        val roles = requireContext().resources.getStringArray(R.array.roles_array)

        // Buat adapter untuk AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roles)

        // Set adapter ke AutoCompleteTextView
        binding.etRole.setAdapter(adapter)

        // Atur aksi yang akan diambil saat opsi dipilih
        binding.etRole.setOnItemClickListener { parent, _, position, _ ->
            val selectedRole = parent.getItemAtPosition(position).toString()
            // Lakukan sesuatu dengan opsi yang dipilih
            Toast.makeText(requireContext(), "Selected Role: $selectedRole", Toast.LENGTH_SHORT).show()
        }

        binding.etRole.setOnClickListener {
            binding.etRole.showDropDown()
        }
    }

    private fun observeLiveData() {

        viewModel.accountLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    showMessage("Berhasil disimpan")
                    openHome()
                }

                is Resource.Error -> {
                    showMessage(it.message!!)
                }

                is Resource.Loading -> {}
            }
        }
    }

    private fun getData() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val email = binding.etEmail.text.toString()
        val role = binding.etRole.text.toString()

        account?.username = username
        account?.password = password
        account?.email = email
        account?.role = role
    }

    private fun openHome() {

        findNavController().popBackStack()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param account Account.
         * @return A new instance of fragment EditUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(account: Account) =
            EditUserFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARGS.ACCOUNT, account)
                }
            }
    }
}