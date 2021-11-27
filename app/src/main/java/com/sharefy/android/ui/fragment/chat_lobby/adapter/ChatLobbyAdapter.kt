package com.sharefy.android.ui.fragment.chat_lobby.adapter

import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemAdvertContributeBinding
import com.sharefy.android.databinding.ItemChatLobbyBinding
import com.sharefy.android.databinding.ItemNecessaryMaterialsBinding
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.model.User
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener

class ChatLobbyAdapter(
    private val user: User,
    private val data: List<ChatLobby>,
    private val onClickListener: (ChatLobby) -> Unit) : BaseAdapter<ItemChatLobbyBinding, ChatLobby>(data) {

    override val layoutId: Int = R.layout.item_chat_lobby

    override fun bind(binding: ItemChatLobbyBinding, item: ChatLobby, position: Int) {
        binding.chatLobby = item
        binding.textViewName.text = item.personEmails.first { user.email != it }
        binding.textViewLastmessages.text = item.messages.lastOrNull()?.message ?: ""

        binding.root.setOnClickListener {
            onClickListener(item)
        }
    }
}