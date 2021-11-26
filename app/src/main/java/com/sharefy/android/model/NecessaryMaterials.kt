package com.sharefy.android.model

import com.sharefy.android.base.ListAdapterItem

data class NecessaryMaterials(
    val count: Int,
    val information: String ,
) : ListAdapterItem {
    override val id: Long
        get() = 0
}
