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
import com.sharefy.android.base.validator.EmptyValidator
import com.sharefy.android.base.validator.NecessaryMaterialsListValidator
import com.sharefy.android.base.validator.NecessaryMaterialsValidator
import com.sharefy.android.databinding.FragmentAddNewAdvertBinding
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapter
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import multipleValidations

@AndroidEntryPoint
class AddNewAdvertFragment : BaseFragment<FragmentAddNewAdvertBinding, NewAdvertViewModel>(),
    NecessaryMaterialsAdapterClickListener {

    override val layoutId: Int = R.layout.fragment_add_new_advert

    override val viewModel: NewAdvertViewModel by viewModels()

    private val spinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.appSession.categories.orEmpty()
        )
    }

    private val materialList: MutableList<NecessaryMaterials> = mutableListOf()

    private val necessaryMaterialsAdapter by lazy {
        NecessaryMaterialsAdapter(materialList, this)
    }

    override fun onReady(savedInstanceState: Bundle?) {
        binding.apply {
            spinnerProfile.adapter = spinnerAdapter
            recyclerViewExtraItem.adapter = necessaryMaterialsAdapter
        }


        binding.buttonAddNewAdvert.multipleValidations(buttonClickListener = {
            val title = binding.inputEditTextTitle.text.toString()
            val extraNotes = binding.inputEditTextExtraNotes.text.toString()

            hashMapOf(
                Pair(binding.inputLayoutTitle, EmptyValidator(title).validate()),
                Pair(binding.inputLayoutExtraNotes, EmptyValidator(extraNotes).validate()),
                Pair(binding.inputLayoutExtraItem, NecessaryMaterialsListValidator(materialList).validate()),
            )
        }, onSuccessCallback = {
            val title = binding.inputEditTextTitle.text.toString()
            val extraNotes = binding.inputEditTextExtraNotes.text.toString()

        })

        binding.inputLayoutExtraItem.setStartIconOnClickListener {
            val input = binding.inputEditTextExtraItem.text.toString()

            val inputValidation = NecessaryMaterialsValidator(input).validate()

            if (!inputValidation.isSuccess) {
                binding.inputLayoutExtraItem.error = getString(inputValidation.message)
                return@setStartIconOnClickListener
            } else
                binding.inputLayoutExtraItem.error = null


            materialList.add(parseNecessaryMaterial(input))
            necessaryMaterialsAdapter.submitList(materialList)
        }
    }

    private fun parseNecessaryMaterial(input: String): NecessaryMaterials {
        val firstBlankInd = input.indexOf(" ")
        val count = input.substring(0, firstBlankInd)
        val information = input.substring(firstBlankInd+1)

        return NecessaryMaterials(count.toIntOrNull() ?: 0, information)
    }

    override fun onDeleteMaterialClicked(item: NecessaryMaterials, position: Int) {
        materialList.removeAt(position)
        necessaryMaterialsAdapter.submitList(materialList)
    }

}