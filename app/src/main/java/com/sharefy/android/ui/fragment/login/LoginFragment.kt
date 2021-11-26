package com.sharefy.android.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        binding.apply {

        }
    }

}