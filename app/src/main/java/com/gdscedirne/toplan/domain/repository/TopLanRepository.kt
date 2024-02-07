package com.gdscedirne.toplan.domain.repository

import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.User
import kotlinx.coroutines.flow.Flow

interface TopLanRepository {

    fun signInUserWithEmailAndPassword(
        user: User,
        onNavigate: () -> Unit,
    ): Flow<ResponseState<Unit>>

    fun signUpWithEmailAndPassword(
        user: User,
        onNavigate: () -> Unit,
    ): Flow<ResponseState<Unit>>

    fun saveUser(user: User): Flow<ResponseState<Unit>>

    fun signOut(): Flow<ResponseState<Unit>>

    fun isUserSignedIn(): Flow<ResponseState<Boolean>>
}