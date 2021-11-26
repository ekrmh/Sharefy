package com.sharefy.android.ui.fragment.register

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val layoutId = R.layout.fragment_register
    override val viewModel: RegisterViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

    }

}