package com.sharefy.android.base

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


abstract class BaseRepository {

    fun <T> safeFlowCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ): Flow<T> =
        flow {
            val response = block.invoke()
            emit(response)
        }.catch { ex ->
            throw ex
        }.flowOn(dispatcher)

    fun addFlowCall(
        collection: CollectionReference,
        data: BaseModel
    ): Flow<Void> = safeFlowCall {
        val document = collection.document()
        val id = document.id
        data.docId = id
        collection.document(id).set(data).await()
    }

    inline fun <reified T> getFlowCall(
        collection: CollectionReference,
    ): Flow<List<T>> = safeFlowCall {
        val response = collection.get().await()
        response.toObjects(T::class.java)
    }

    fun updateFlowCall(
        collection: CollectionReference,
        docId: String,
        data: Map<String, Any>
    ): Flow<Void> = safeFlowCall {
        collection.document(docId).update(data).await()
    }

}