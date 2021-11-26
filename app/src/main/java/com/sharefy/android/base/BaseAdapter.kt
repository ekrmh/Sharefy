package com.sharefy.android.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


abstract class BaseAdapter<BINDING : ViewDataBinding, T : ListAdapterItem>(
    diffCallback: DiffUtil.ItemCallback<T> = ListAdapterItemDiffCallback()
) : ListAdapter<T, BaseViewHolder<BINDING, T>>(diffCallback) {

    @get:LayoutRes
    protected abstract val layoutId: Int

    open var onItemClickListener: ((Int, T) -> Unit)? = null

    protected var onItemBinding: ((BINDING) -> Unit)? = null

    protected abstract fun bindItem(binding: BINDING, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BINDING, T> {
        val binding = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return BaseViewHolder<BINDING, T>(binding).apply {
            onItemBinding = this@BaseAdapter.onItemBinding
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BINDING, T>, position: Int) {
        getItem(position)?.let { item ->
            holder.apply {
                bindItem(item)
                itemView.setOnClickListener {
                    onItemClickListener?.invoke(position, item)
                }
            }
        }
    }
}