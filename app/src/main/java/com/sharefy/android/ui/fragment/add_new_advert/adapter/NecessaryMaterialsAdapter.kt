package com.sharefy.android.ui.fragment.add_new_advert.adapter

import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemNecessaryMaterialsBinding
import com.sharefy.android.model.NecessaryMaterials

class NecessaryMaterialsAdapter(
    private val data: List<NecessaryMaterials>,
    private val necessaryMaterialsAdapterClickListener: NecessaryMaterialsAdapterClickListener,
) : BaseAdapter<ItemNecessaryMaterialsBinding, NecessaryMaterials>(data) {

    override val layoutId: Int = R.layout.item_necessary_materials

    override fun bind(
        binding: ItemNecessaryMaterialsBinding,
        item: NecessaryMaterials,
        position: Int,
    ) {
        binding.textViewMaterialCount.text = item.count.toString()
        binding.item = item

        binding.buttonDelete.setOnClickListener {
            necessaryMaterialsAdapterClickListener.onDeleteMaterialClicked(
                item, position
            )
        }
    }
}

interface NecessaryMaterialsAdapterClickListener {
    fun onDeleteMaterialClicked(item: NecessaryMaterials, position: Int)
}