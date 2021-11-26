package com.sharefy.android.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSampleRepository(@SampleReference reference: CollectionReference): SampleRepository =
        SampleRepositoryImp(reference)

    @Provides
    @Singleton
    fun provideUserRepository(auth: FirebaseAuth, @UserReference reference: CollectionReference): UserRepository =
        UserRepositoryImp(auth, reference)

    @Provides
    @Singleton
    fun provideCategoryRepository(@CategoryReference reference: CollectionReference): CategoryRepository =
        CategoryRepositoryImp(reference)
}