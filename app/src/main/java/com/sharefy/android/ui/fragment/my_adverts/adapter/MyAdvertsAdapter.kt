package com.sharefy.android.ui.fragment.my_adverts.adapter

import android.view.View
import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemAdvertsBinding
import com.sharefy.android.model.Advert

class MyAdvertsAdapter(
    private val adverts: List<Advert>,
    private val advertsClickListener: MyAdvertsClickListener,
) : BaseAdapter<ItemAdvertsBinding, Advert>(adverts) {

    override val layoutId: Int = R.layout.item_adverts

    override fun bind(binding: ItemAdvertsBinding, item: Advert, position: Int) {

        binding.root.setOnClickListener {
            advertsClickListener.onItemClicked(item, position)
        }

        binding.progressPendingContribution.setOnClickListener {
            advertsClickListener.onPendingContributionClicked(item, position)
        }

        binding.progressFinishAdvert.setOnClickListener {

        }

        binding.apply {
            advert = item
            progressAdvert.progress = initProgressStatus(item)
            textViewProgressNumber.text = initProgressStatus(item).toString()
            executePendingBindings()
        }
    }

    private fun initProgressStatus(advert: Advert): Int {
        var totalNumber = advert.necessaryMaterial.sumOf { it.count }
        var completedNumber =
            advert.necessaryMaterial.sumOf { it.approvedContribution.sumOf { it.count } }


        return ((completedNumber.toFloat() / totalNumber.toFloat()) * 100).toInt()
    }
}

interface MyAdvertsClickListener {
    fun onItemClicked(advert: Advert, position: Int)
    fun onFinishTaskClicked(advert: Advert, position: Int)
    fun onPendingContributionClicked(advert: Advert, position: Int)
}