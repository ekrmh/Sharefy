package com.sharefy.android.ui.fragment.register

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentRegisterBinding
import com.sharefy.android.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val layoutId = R.layout.fragment_register

    override val viewModel: RegisterViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        initUI()
        viewModel.goToMain.observeNonNull(this){
            if (it){
                startActivity(MainActivity.newIntent(requireContext()))
                requireActivity().finish()
            }
        }

        binding.btnConfirm.setOnClickListener {
            val username = binding.textFieldUsername.editText?.text.toString()
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.textFieldPassword.editText?.text.toString()
            viewModel.register(username, email, password)
        }
    }

    private fun initUI(){
        binding.editTextEmail.hint = SpannableStringBuilder(getString(R.string.email))
        binding.editTextPassword.hint = SpannableStringBuilder(getString(R.string.password))
        binding.editTextUsername.hint = SpannableStringBuilder(getString(R.string.username))
    }

}