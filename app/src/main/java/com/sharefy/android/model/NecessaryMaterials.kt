package com.sharefy.android.model

import android.os.Parcelable
import com.sharefy.android.base.ListAdapterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class NecessaryMaterials(
    var count: Int = 0,
    val information: String = "",
    val completedNumber: Int = 0,
    var pendingContribution: MutableList<ContributeAdvert> = mutableListOf(),
    var approvedContribution: MutableList<ContributeAdvert> = mutableListOf(),
    var updatable : Boolean = true
) : ListAdapterItem, Parcelable {
    override val id: Long
        get() = 0
}
