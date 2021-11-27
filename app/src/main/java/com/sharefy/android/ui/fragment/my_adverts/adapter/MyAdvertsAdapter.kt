package com.sharefy.android.ui.fragment.my_adverts.adapter

import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemAdvertsBinding
import com.sharefy.android.model.Advert

class MyAdvertsAdapter(
    private val adverts: List<Advert>,
) : BaseAdapter<ItemAdvertsBinding, Advert>(adverts) {

    override val layoutId: Int = R.layout.item_adverts

    override fun bind(binding: ItemAdvertsBinding, item: Advert, position: Int) {
        binding.apply {
            advert = item
            progressAdvert.progress = initProgressStatus(item)
            textViewProgressNumber.text = initProgressStatus(item).toString()
            executePendingBindings()
        }
    }

    private fun initProgressStatus(advert: Advert): Int {
        var totalNumber = 0
        var completedNumber = 0
        advert.necessaryMaterial.forEach { necessaryMaterials ->
            totalNumber += necessaryMaterials.count
            completedNumber += necessaryMaterials.completedNumber
        }

        return ((completedNumber.toFloat() / totalNumber.toFloat()) * 100).toInt()
    }
}