package com.sharefy.android.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentLoginBinding
import com.sharefy.android.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

        viewModel.goToMain.observeNonNull(this){
            if (it){
                startActivity(MainActivity.newIntent(requireContext()))
                requireActivity().finish()
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.textFieldPassword.editText?.text.toString()
            val rememberMe = binding.checkboxRememberMe.isChecked

            viewModel.login(email, password, rememberMe)
        }
    }

}