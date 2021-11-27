package com.sharefy.android.repository

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.Advert
import com.sharefy.android.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface AdvertRepository {
    suspend fun postNewAdvert(newAdvert: Advert): Flow<Void>
    suspend fun getAdverts(): Flow<List<Advert>>
    suspend fun getMyAdverts(userId: String): Flow<List<Advert>>
}

class AdvertRepositoryImp @Inject constructor(
    private val advertCollection: CollectionReference,
) : AdvertRepository, BaseRepository() {

    override suspend fun postNewAdvert(newAdvert: Advert): Flow<Void> {
        return addFlowCall(advertCollection, newAdvert)
    }

    override suspend fun getAdverts(): Flow<List<Advert>> {
        return getFlowCall(advertCollection)
    }

    override suspend fun getMyAdverts(userId: String): Flow<List<Advert>> {
        return safeFlowCall {
            val result = advertCollection.get().await()
            val collection = result.toObjects(Advert::class.java)
            collection.filter {
               it.userId == userId
            }
        }
    }
}