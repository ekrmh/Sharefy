package com.sharefy.android.model

import android.os.Parcelable
import com.sharefy.android.base.ListAdapterItem
import kotlinx.parcelize.Parcelize


@Parcelize
data class ContributeAdvert(
    val userId : String = "",
    val count : Int = 0
): Parcelable, ListAdapterItem {
    override val id: Long
        get() = 0
}