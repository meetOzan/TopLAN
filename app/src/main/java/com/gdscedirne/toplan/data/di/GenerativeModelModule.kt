package com.gdscedirne.toplan.data.di

import com.gdscedirne.toplan.BuildConfig
import com.gdscedirne.toplan.common.Constants.GEMINI_MODEL
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenerativeModelModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL,
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

}