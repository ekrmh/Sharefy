package com.sharefy.android.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ListAdapterItemDiffCallback<T : ListAdapterItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
