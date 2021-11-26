package com.sharefy.android.ui.activity.before_login

import android.os.Bundle
import androidx.activity.viewModels
import com.sharefy.android.R
import com.sharefy.android.databinding.ActivityBeforeLoginBinding
import com.sharefy.android.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeforeLoginActivity : BaseActivity<ActivityBeforeLoginBinding, BeforeLoginViewModel>() {

    override val layoutId = R.layout.activity_before_login

    override val viewModel: BeforeLoginViewModel by viewModels()

    override val fragmentContainerId = R.id.navHostFragmentContainer

    override fun onReady(savedInstanceState: Bundle?) {
        withToolbar(binding.navHostFragmentContainer, binding.toolbar)
    }

}