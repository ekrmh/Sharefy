package com.sharefy.android.ui.fragment.chat.adapter

import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import com.sharefy.android.R
import com.sharefy.android.base.BaseAdapter
import com.sharefy.android.databinding.ItemAdvertContributeBinding
import com.sharefy.android.databinding.ItemChatBinding
import com.sharefy.android.databinding.ItemChatLobbyBinding
import com.sharefy.android.databinding.ItemNecessaryMaterialsBinding
import com.sharefy.android.model.Chat
import com.sharefy.android.model.ChatLobby
import com.sharefy.android.model.NecessaryMaterials
import com.sharefy.android.model.User
import com.sharefy.android.ui.fragment.add_new_advert.adapter.NecessaryMaterialsAdapterClickListener

class ChatAdapter(
    private val user: User,
    private val data: List<Chat>) : BaseAdapter<ItemChatBinding, Chat>(data) {

    override val layoutId: Int = R.layout.item_chat

    override fun bind(binding: ItemChatBinding, item: Chat, position: Int) {
        binding.chat = item
        binding.textView.text = item.message
        if (item.senderId == user.docId){
            binding.cardView.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.indigo_blue))
            binding.textView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            binding.textView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        }
    }
}