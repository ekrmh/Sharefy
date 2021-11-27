package com.sharefy.android.model

import com.sharefy.android.base.BaseModel
import com.sharefy.android.base.ListAdapterItem

class ChatLobby(
    val personIds: List<String> = listOf(),
    val personEmails: List<String> = listOf(),
    val category: Category?=null,
    val title: String = "",
    val messages: MutableList<Chat> = mutableListOf()
):BaseModel(), ListAdapterItem {
    override val id: Long
        get() = 0
}

