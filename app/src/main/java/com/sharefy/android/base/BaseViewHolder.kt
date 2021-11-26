package com.sharefy.android.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sharefy.android.BR

class BaseViewHolder<BINDING : ViewDataBinding, T : ListAdapterItem>(val binding: BINDING) :
    RecyclerView.ViewHolder(binding.root){

    var onItemBinding: ((BINDING) -> Unit)? = null

    fun bindItem(item: T) {
        binding.setVariable(BR.item, item)
        onItemBinding?.invoke(binding)
        binding.executePendingBindings()
    }
}