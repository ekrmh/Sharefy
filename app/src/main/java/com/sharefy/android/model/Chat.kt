package com.sharefy.android.model

import com.sharefy.android.base.BaseModel
import com.sharefy.android.base.ListAdapterItem

class Chat(
    val senderId: String = "",
    val message: String = ""
): BaseModel(), ListAdapterItem {
    override val id: Long
        get() = 0
}
