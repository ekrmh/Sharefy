package com.sharefy.android.model

import android.os.Parcelable
import com.sharefy.android.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
class Category(val name: String="", val markerColor: String=""): BaseModel(), Parcelable{
    override fun toString(): String {
        return name
    }
}