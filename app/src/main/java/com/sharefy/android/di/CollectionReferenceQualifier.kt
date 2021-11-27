package com.sharefy.android.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SampleReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoryReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdvertReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChatReference