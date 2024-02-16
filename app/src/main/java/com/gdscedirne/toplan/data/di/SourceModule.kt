package com.gdscedirne.toplan.data.di

import com.gdscedirne.toplan.data.source.FirebaseSourceImpl
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): FirebaseSource {
        return FirebaseSourceImpl(
            firebaseAuth,
            firebaseFirestore,
            firebaseStorage
        )
    }

}