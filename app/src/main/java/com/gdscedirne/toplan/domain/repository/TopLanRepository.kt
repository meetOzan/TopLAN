package com.gdscedirne.toplan.domain.repository

import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.data.model.User
import kotlinx.coroutines.flow.Flow

interface TopLanRepository {

    // User - Auth
    fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onNavigate: () -> Unit,
    ): Flow<ResponseState<Unit>>

    fun signUpWithEmailAndPassword(
        user: User,
        onNavigate: () -> Unit,
    ): Flow<ResponseState<Unit>>

    fun saveUser(user: User): Flow<ResponseState<Unit>>

    fun getUser(): Flow<ResponseState<User>>

    fun signOut(): Flow<ResponseState<Unit>>

    fun isUserSignedIn(): Flow<ResponseState<Boolean>>

    // Marker
    fun addMarker(marker: Marker): Flow<ResponseState<Unit>>

    fun getMarkers(): Flow<ResponseState<List<Marker>>>

    fun getCurrentDate(): String

    fun getCurrentTime(): String

}