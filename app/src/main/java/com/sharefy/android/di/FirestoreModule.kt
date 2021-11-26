package com.sharefy.android.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @SampleReference
    fun provideSampleCollection(firestore: FirebaseFirestore) = firestore.collection("Sample")

    @Provides
    @Singleton
    @UserReference
    fun provideUserCollection(firestore: FirebaseFirestore) = firestore.collection("User")

    @Provides
    @Singleton
    @CategoryReference
    fun provideCategoryCollection(firestore: FirebaseFirestore) = firestore.collection("Categories")
}