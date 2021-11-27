package com.sharefy.android.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentSplashBinding
import com.sharefy.android.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        viewModel.getUserData()
    }

}