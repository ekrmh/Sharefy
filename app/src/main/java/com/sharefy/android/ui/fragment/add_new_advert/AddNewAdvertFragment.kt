package com.sharefy.android.ui.fragment.add_new_advert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentAddNewAdvertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewAdvertFragment : BaseFragment<FragmentAddNewAdvertBinding, NewAdvertViewModel>() {

    override val layoutId: Int = R.layout.fragment_add_new_advert

    override val viewModel: NewAdvertViewModel by viewModels()

    private val spinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            requireContext().resources.getStringArray(R.array.Category)
        )
    }

    override fun onReady(savedInstanceState: Bundle?) {
        binding.spinnerProfile.adapter = spinnerAdapter


    }

}