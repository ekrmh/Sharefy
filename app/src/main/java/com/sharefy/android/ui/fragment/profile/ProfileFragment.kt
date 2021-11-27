package com.sharefy.android.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentMyAdvertsBinding
import com.sharefy.android.model.Advert
import com.sharefy.android.ui.fragment.my_adverts.adapter.MyAdvertsAdapter
import com.sharefy.android.ui.fragment.my_adverts.adapter.MyAdvertsClickListener
import dagger.hilt.android.AndroidEntryPoint
import observeNonNull

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentMyAdvertsBinding, ProfileViewModel>(){

    override val layoutId: Int = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {

    }

}