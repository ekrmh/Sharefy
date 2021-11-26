package com.sharefy.android.di

import com.google.firebase.firestore.CollectionReference
import com.sharefy.android.repository.SampleRepository
import com.sharefy.android.repository.SampleRepositoryImp
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
    fun provideCivilizationRepository(@SampleReference reference: CollectionReference): SampleRepository =
        SampleRepositoryImp(reference)

}