package com.sharefy.android.repository

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.Sample
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SampleRepository {
    suspend fun postData(): Flow<Void>
    suspend fun getData(): Flow<List<Sample>>
    suspend fun updateData(): Flow<Void>
}

class SampleRepositoryImp @Inject constructor(private val collection: CollectionReference) :
    SampleRepository, BaseRepository() {

    override suspend fun postData(): Flow<Void> {
        return addFlowCall(collection, Sample("Sample"))
    }

    override suspend fun getData(): Flow<List<Sample>> {
        return getFlowCall(collection)
    }

    override suspend fun updateData(): Flow<Void> {
        return updateFlowCall(
            collection, "sample", mapOf(
                "message" to "new_sample"
            )
        )
    }


}

