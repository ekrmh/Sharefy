package com.sharefy.android.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.sharefy.android.base.BaseRepository
import com.sharefy.android.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface UserRepository {
    suspend fun createUserWithEmailAndPassword(user: User): Flow<AuthResult>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<AuthResult>
    suspend fun signOut(): Flow<Unit>
    suspend fun saveUserData(user: User): Flow<Void>
    suspend fun getUserData(email: String): Flow<User>
}

class UserRepositoryImp @Inject constructor(private val auth: FirebaseAuth, private val collection: CollectionReference) :
    UserRepository, BaseRepository() {

    override suspend fun createUserWithEmailAndPassword(user: User) = safeFlowCall {
        auth.createUserWithEmailAndPassword(user.email, user.password).await()
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) = safeFlowCall {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() = safeFlowCall {
        auth.signOut()
    }

    override suspend fun saveUserData(user: User) = safeFlowCall {
        collection.document(user.docId).set(user).await()
    }

    override suspend fun getUserData(email: String) = safeFlowCall {
        val response = collection.whereEqualTo("email", email).limit(1).get().await()
        response.toObjects(User::class.java).first()
    }


}