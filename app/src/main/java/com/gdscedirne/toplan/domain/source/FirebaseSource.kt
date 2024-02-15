package com.gdscedirne.toplan.domain.source

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

    // Marker
    fun addMarker(marker: Marker)

    suspend fun getMarkers(): List<Marker>

}