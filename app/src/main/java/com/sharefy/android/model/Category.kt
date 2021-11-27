package com.sharefy.android.model

import com.sharefy.android.base.BaseModel

class Category(val name: String="", val markerColor: String="#FFFFFF"): BaseModel(){
    override fun toString(): String {
        return name
    }
}