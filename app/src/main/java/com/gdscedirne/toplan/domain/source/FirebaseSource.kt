package com.gdscedirne.toplan.domain.source

import com.gdscedirne.toplan.data.User

interface FirebaseSource {

    // User - Auth
    fun signUpUserWithEmailAndPassword(user: User, onNavigate: () -> Unit)

    fun signInWithEmailAndPassword(email: String, password: String, onNavigate: () -> Unit)

    fun saveUser(user: User)

    fun signOut()

    fun isUserSignedIn(): Boolean

}