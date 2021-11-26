package com.sharefy.android.model

import com.sharefy.android.base.BaseModel

data class Advert(
    val title: String = "",
    val additionalInformation: String = "",
    val categoryId: String = "",
    val necessaryMaterial: List<NecessaryMaterials> = listOf(),
    val lat: Double = 0.0,
    val long: Double = 0.0,
) : BaseModel()
