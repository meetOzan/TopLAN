package com.gdscedirne.toplan.domain.repository

import android.content.Context
import android.net.Uri
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.Feed
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

    fun getUserById(userId: String): Flow<ResponseState<User>>

    fun signOut(): Flow<ResponseState<Unit>>

    fun isUserSignedIn(): Flow<ResponseState<Boolean>>

    fun updateProfile(user: User): Flow<ResponseState<Unit>>

    // Upload Image
    fun uploadImageToStorage(
        uri: Uri,
        context: Context,
        onSuccess: (String, String) -> Unit,
        onFailure: (String) -> Unit,
    ): Flow<ResponseState<Unit>>

    fun uploadImageToFirestore(
        imagesUrl: List<String>,
        imageName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ): Flow<ResponseState<Unit>>

    // Marker
    fun addMarker(marker: Marker): Flow<ResponseState<Unit>>

    fun getMarkers(): Flow<ResponseState<List<Marker>>>

    fun getCurrentDate(): String

    fun getCurrentTime(): String

    // Gemini - Chat
    fun askQuestion(question: String): Flow<ResponseState<String>>

    // Feed
    fun addFeed(feed: Feed): Flow<ResponseState<Unit>>

    fun getFeed(): Flow<ResponseState<List<Feed>>>

}