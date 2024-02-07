package com.gdscedirne.toplan.data.source

import com.gdscedirne.toplan.data.User
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseSource {

    override fun signUpUserWithEmailAndPassword(user: User, onNavigate: () -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { response ->
                if (response.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    val userMap = hashMapOf(
                        "id" to currentUser?.uid.toString(),
                        "name" to user.name,
                        "surname" to user.surname,
                        "relativeName" to user.relativeName,
                        "address" to user.address,
                        "phone" to user.phone,
                        "email" to user.email,
                        "password" to user.password,
                    )
                    firebaseFirestore.collection("users").document(currentUser?.uid.toString())
                        .set(userMap).addOnCompleteListener {
                            if (it.isSuccessful) {
                                onNavigate()
                            } else {
                                throw RuntimeException(response.result.toString())
                            }
                        }
                } else {
                    throw RuntimeException(response.exception)
                }
            }
    }

    override fun signInWithEmailAndPassword(user: User, onNavigate: () -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onNavigate()
                } else {
                    throw RuntimeException(task.exception)
                }
            }
    }

    override fun saveUser(user: User) {
        val currentUser = firebaseAuth.currentUser
        val userMap = hashMapOf(
            "id" to currentUser?.uid.toString(),
            "name" to user.name,
            "surname" to user.surname,
            "relativeName" to user.relativeName,
            "address" to user.address,
            "phone" to user.phone,
            "email" to user.email,
            "password" to user.password,
        )
        firebaseFirestore.collection("users").document(currentUser?.uid.toString())
            .set(userMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    throw RuntimeException(it.result.toString())
                } else {
                    throw RuntimeException(it.exception)
                }
            }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}