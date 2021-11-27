package com.sharefy.android.ui.fragment.register

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.validator.EmailValidator
import com.sharefy.android.base.validator.EmptyValidator
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.databinding.FragmentRegisterBinding
import com.sharefy.android.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import multipleValidations
import observeNonNull

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val layoutId = R.layout.fragment_register

    override val viewModel: RegisterViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        initUI()
        viewModel.goToMain.observeNonNull(this) {
            if (it) {
                startActivity(MainActivity.newIntent(requireContext()))
                requireActivity().finish()
            }
        }

        binding.btnConfirm.multipleValidations(
            buttonClickListener = {
                val username = binding.textFieldUsername.editText?.text.toString()
                val email = binding.textFieldEmail.editText?.text.toString()
                val password = binding.textFieldPassword.editText?.text.toString()

                hashMapOf(
                    Pair(binding.textFieldUsername, EmptyValidator(username).validate()),
                    Pair(binding.textFieldEmail, BaseValidator.validate(EmptyValidator(email), EmailValidator(email))),
                    Pair(binding.textFieldPassword, EmptyValidator(password).validate()),
                )

            }, onSuccessCallback = {
                val username = binding.textFieldUsername.editText?.text.toString()
                val email = binding.textFieldEmail.editText?.text.toString()
                val password = binding.textFieldPassword.editText?.text.toString()
                viewModel.register(username, email, password)
            }
        )
    }

    private fun initUI() {
        binding.editTextEmail.hint = SpannableStringBuilder(getString(R.string.email))
        binding.editTextPassword.hint = SpannableStringBuilder(getString(R.string.password))
        binding.editTextUsername.hint = SpannableStringBuilder(getString(R.string.username))
    }

}