package com.sharefy.android.ui.fragment.add_new_advert

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.base.validator.EmptyValidator
import com.sharefy.android.base.validator.NecessaryMaterialsListValidator
import com.sharefy.android.base.validator.NecessaryMaterialsValidator
import com.sharefy.android.databinding.FragmentAddNewAdvertBinding
import com.sharefy.android.model.Advert
import com.sharefy.android.model.Category
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapter
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import multipleValidations
import toast

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
    private val args by navArgs<AddNewAdvertFragmentArgs>()

    private val materialList: MutableList<NecessaryMaterials> = mutableListOf()

    private val necessaryMaterialsAdapter by lazy {
        NecessaryMaterialsAdapter(materialList, this)
    }

    override fun onReady(savedInstanceState: Bundle?) {
        initUI()
        binding.apply {
            spinnerCategory.adapter = spinnerAdapter
            recyclerViewExtraItem.adapter = necessaryMaterialsAdapter
        }


        binding.buttonAddNewAdvert.multipleValidations(buttonClickListener = {
            val title = binding.inputEditTextTitle.text.toString()
            val extraNotes = binding.inputEditTextExtraNotes.text.toString()

            hashMapOf(
                Pair(binding.inputLayoutTitle, EmptyValidator(title).validate()),
                Pair(binding.inputLayoutExtraNotes, EmptyValidator(extraNotes).validate()),
            )
        }, onSuccessCallback = {
            val title = binding.inputEditTextTitle.text.toString()
            val extraNotes = binding.inputEditTextExtraNotes.text.toString()

            val category = binding.spinnerCategory.selectedItem as Category

            val lastVal = NecessaryMaterialsListValidator(materialList).validate()

            if (!lastVal.isSuccess) {
                requireContext().toast(getString(lastVal.message))
                return@multipleValidations
            }

            viewModel.addNewAdvert(
                Advert(
                    userId = viewModel.appSession.user.docId,
                    title = title,
                    additionalInformation = extraNotes,
                    category = category,
                    necessaryMaterial = materialList,
                    lat = args.latLng.latitude,
                    long = args.latLng.longitude
                )
            )

        })

        binding.buttonAddItem.multipleValidations(buttonClickListener = {
            val itemCount = binding.inputEditTextExtraItem.text.toString()
            val itemName = binding.inputEditTextExtraItemName.text.toString()

            hashMapOf(
                Pair(binding.inputLayoutExtraItem, EmptyValidator(itemCount).validate()),
                Pair(binding.inputLayoutExtraItemName, EmptyValidator(itemName).validate()),
            )
        }, onSuccessCallback = {
            val itemCount = binding.inputEditTextExtraItem.text.toString()
            val itemName = binding.inputEditTextExtraItemName.text.toString()

            val itemCountInt = itemCount.toIntOrNull()

            if (itemCountInt == null) {
                binding.inputLayoutExtraItem.error =
                    getString(R.string.validation_error_necessary_items_count)
                return@multipleValidations
            }

            materialList.add(
                NecessaryMaterials(itemCountInt, itemName)
            )
            necessaryMaterialsAdapter.updateData(materialList)

        })
    }

    override fun onMaterialItemClicked(item: NecessaryMaterials, position: Int) {
        materialList.removeAt(position)
        necessaryMaterialsAdapter.updateData(materialList)
    }

    private fun initUI() {
        binding.inputEditTextTitle.hint =
            SpannableStringBuilder(getString(R.string.new_advert_title))
        binding.inputEditTextExtraNotes.hint =
            SpannableStringBuilder(getString(R.string.new_advert_extra_notes))
        binding.inputEditTextExtraItem.hint =
            SpannableStringBuilder(getString(R.string.new_advert_item_count))
        binding.inputEditTextExtraItemName.hint =
            SpannableStringBuilder(getString(R.string.new_advert_item_name))
    }

}