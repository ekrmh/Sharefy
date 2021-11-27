package com.sharefy.android.ui.fragment.login

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.validator.EmailValidator
import com.sharefy.android.base.validator.EmptyValidator
import com.sharefy.android.base.validator.base.BaseValidator
import com.sharefy.android.databinding.FragmentLoginBinding
import com.sharefy.android.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import multipleValidations
import observeNonNull

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        initUI()
        viewModel.getCategories()

        viewModel.goToMain.observeNonNull(this) {
            if (it) {
                startActivity(MainActivity.newIntent(requireContext()))
                requireActivity().finish()
            }
        }

        binding.btnLogin.multipleValidations(buttonClickListener = {
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.textFieldPassword.editText?.text.toString()

            val emailValidator = BaseValidator.validate(
                EmptyValidator(email) , EmailValidator(email)
            )
            val passwordValidator = EmptyValidator(password).validate()

            hashMapOf(
                Pair(binding.textFieldEmail, emailValidator),
                Pair(binding.textFieldPassword, passwordValidator),
            )

        }, onSuccessCallback = {
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.textFieldPassword.editText?.text.toString()
            val rememberMe = binding.checkboxRememberMe.isChecked

            viewModel.login(email, password, rememberMe)
        })

    }

    private fun initUI() {
        binding.editTextEmail.hint = SpannableStringBuilder(getString(R.string.email))
        binding.editTextPassword.hint = SpannableStringBuilder(getString(R.string.password))
    }

}