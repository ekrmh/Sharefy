package com.sharefy.android.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseActivity
import com.sharefy.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModels()

    override val fragmentContainerId = R.id.navHostFragmentContainer

    override fun onReady(savedInstanceState: Bundle?) {
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}