package com.sharefy.android.model

import com.sharefy.android.base.ListAdapterItem

data class NecessaryMaterials(
    val count: Int = 0,
    val information: String = "",
    val completedNumber: Int = 10,
) : ListAdapterItem {
    override val id: Long
        get() = 0
}
