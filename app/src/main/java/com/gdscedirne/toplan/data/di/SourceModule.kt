package com.gdscedirne.toplan.data.di

import com.gdscedirne.toplan.data.source.FirebaseSourceImpl
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Provides
    @Singleton
    fun provideFirebaseSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseSource {
        return FirebaseSourceImpl(
            firebaseAuth,
            firebaseFirestore
        )
    }

}