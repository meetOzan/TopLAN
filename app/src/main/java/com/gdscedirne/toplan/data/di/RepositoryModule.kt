package com.gdscedirne.toplan.data.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.gdscedirne.toplan.data.repository.TopLanRepositoryImpl
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import com.gdscedirne.toplan.domain.source.FirebaseSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideTopLanRepository(
        firebaseSource: FirebaseSource
    ): TopLanRepository {
        return TopLanRepositoryImpl(
            firebaseSource,
            generativeModel = GenerativeModelModule.provideGenerativeModel()
        )
    }

}