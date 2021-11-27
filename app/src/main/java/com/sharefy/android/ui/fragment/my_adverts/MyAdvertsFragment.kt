package com.sharefy.android.ui.fragment.my_adverts

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentMyAdvertsBinding
import com.sharefy.android.ui.fragment.my_adverts.adapter.MyAdvertsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAdvertsFragment : BaseFragment<FragmentMyAdvertsBinding, MyAdvertsViewModel>() {

    override val layoutId: Int = R.layout.fragment_my_adverts

    override val viewModel: MyAdvertsViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        binding.recyclerViewMyAdverts.adapter = MyAdvertsAdapter(listOf())

        viewModel.fetchMyAdverts(viewModel.appSession.user!!.docId)
    }

}