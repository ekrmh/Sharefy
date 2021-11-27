package com.sharefy.android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ContributeAdvert(
    val userId : String = "",
    val count : Int = 0
): Parcelable