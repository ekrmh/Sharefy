package com.sharefy.android.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.repository.SampleRepository
import com.sharefy.android.repository.SampleRepositoryImp
import com.sharefy.android.repository.UserRepository
import com.sharefy.android.repository.UserRepositoryImp
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
}