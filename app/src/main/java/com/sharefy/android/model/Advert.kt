package com.sharefy.android.model

import android.os.Parcelable
import com.sharefy.android.base.BaseModel
import com.sharefy.android.base.ListAdapterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Advert(
    val userId: String = "",
    val title: String = "",
    val additionalInformation: String = "",
    val category: Category = Category(),
    val necessaryMaterial: MutableList<NecessaryMaterials> = mutableListOf(),
    val lat: Double = 0.0,
    val long: Double = 0.0,
) : BaseModel(), ListAdapterItem, Parcelable {
    override val id: Long
        get() = 0
}
