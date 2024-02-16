package com.gdscedirne.toplan.domain.source

import android.content.Context
import android.net.Uri
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.data.model.User

interface FirebaseSource {

    // User - Auth
    fun signUpUserWithEmailAndPassword(user: User, onNavigate: () -> Unit)

    fun signInWithEmailAndPassword(email: String, password: String, onNavigate: () -> Unit)

    fun saveUser(user: User)

    suspend fun getUser(): User

    fun signOut()

    fun isUserSignedIn(): Boolean

    // Upload Image
    fun uploadImageToStorage(
        uri: Uri,
        context: Context,
        onSuccess: (String, String) -> Unit = { _, _ -> },
        onFailure: (String) -> Unit,
    )

    fun uploadImageToFirestore(
        imagesUrl: List<String>,
        imageName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    )

    // Marker
    fun addMarker(marker: Marker)

    suspend fun getMarkers(): List<Marker>

}