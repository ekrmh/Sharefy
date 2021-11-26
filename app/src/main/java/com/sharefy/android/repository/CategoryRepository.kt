package com.sharefy.android.repository

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CategoryRepository {
    suspend fun getCategories(): Flow<List<Category>>
}

class CategoryRepositoryImp @Inject constructor(private val collection: CollectionReference) :
    CategoryRepository, BaseRepository() {

    override suspend fun getCategories(): Flow<List<Category>> = getFlowCall(collection)

}