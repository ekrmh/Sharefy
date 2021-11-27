package com.sharefy.android.repository

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.Chat
import com.sharefy.android.model.ChatLobby
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ChatRepository {
    suspend fun createMessageRoom(chatLobby: ChatLobby): Flow<Void>
    suspend fun getAllChats(userId: String): Flow<MutableList<ChatLobby>>
    suspend fun getChat(chatLobbyId: String): Flow<ChatLobby>
    fun sendMessage(docId: String, messages: MutableList<Chat>): Flow<Void>
}

class ChatRepositoryImp @Inject constructor(private val collection: CollectionReference) :
    ChatRepository, BaseRepository() {

    override suspend fun createMessageRoom(chatLobby: ChatLobby) =
        addFlowCall(collection, chatLobby)

    override suspend fun getAllChats(userId: String) = safeFlowCall {
        val response = collection.whereArrayContains("personIds", userId).get().await()
        response.toObjects(ChatLobby::class.java)
    }

    override suspend fun getChat(chatLobbyId: String): Flow<ChatLobby> = safeFlowCall {
        val response = collection.document(chatLobbyId).get().await()
        response.toObject(ChatLobby::class.java)!!
    }

    override fun sendMessage(docId: String, messages: MutableList<Chat>): Flow<Void> =
        updateFlowCall(collection,
            docId,
            mapOf("messages" to messages, "lastUpdatedTime" to System.currentTimeMillis()))


}