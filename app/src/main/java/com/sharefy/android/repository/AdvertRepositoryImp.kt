package com.sharefy.android.repository

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.Advert
import com.sharefy.android.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AdvertRepository {
    suspend fun postNewAdvert(newAdvert: Advert): Flow<Void>
}

class AdvertRepositoryImp @Inject constructor(
    private val advertCollection: CollectionReference,
) : AdvertRepository, BaseRepository() {

    override suspend fun postNewAdvert(newAdvert: Advert): Flow<Void> {
        return addFlowCall(advertCollection, newAdvert)
    }
}