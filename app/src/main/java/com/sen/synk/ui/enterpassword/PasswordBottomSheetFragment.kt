package com.sen.synk.ui.enterpassword

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sen.synk.databinding.FragmentBottomSheetPasswordBinding

class PasswordBottomSheetFragment : BottomSheetDialogFragment() {

    interface BottomSheetListener {
        fun onTextEntered(text: String)
    }

    lateinit var binding: FragmentBottomSheetPasswordBinding
    private var listener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val enteredText = binding.etPassword.text.toString()
            listener?.onTextEntered(enteredText)
        }
    }

    fun setBottomSheetListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        binding.etPassword.text = null
        binding.tilPassword.isErrorEnabled = false
    }
}
