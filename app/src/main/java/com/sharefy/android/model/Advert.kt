package com.sharefy.android.model

import com.sharefy.android.base.BaseModel
import com.sharefy.android.base.ListAdapterItem

data class Advert(
    val userId: String = "",
    val title: String = "",
    val additionalInformation: String = "",
    val category: Category = Category(),
    val necessaryMaterial: List<NecessaryMaterials> = listOf(),
    val lat: Double = 0.0,
    val long: Double = 0.0,
) : BaseModel(), ListAdapterItem {
    override val id: Long
        get() = 0
}
