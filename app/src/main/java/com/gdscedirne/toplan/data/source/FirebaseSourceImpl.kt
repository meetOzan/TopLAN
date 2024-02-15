package com.gdscedirne.toplan.data.source

import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
            .addOnFailureListener {
                throw RuntimeException(it.message)
            }
    }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onNavigate: () -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onNavigate()
                } else {
                    throw RuntimeException(task.exception)
                }
            }
            .addOnFailureListener {
                throw RuntimeException(it.message)
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
            .set(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    throw RuntimeException(it.result.toString())
                } else {
                    throw RuntimeException(it.exception)
                }
            }
            .addOnFailureListener {
                throw RuntimeException(it.message)
            }
    }

    override suspend fun getUser(): User {
        val currentUser = firebaseAuth.currentUser
        var user: User
        return suspendCoroutine {continuation ->
            firebaseFirestore.collection("users").document(currentUser?.uid.toString())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        user = document?.data?.let { data ->
                            User(
                                id = data["id"] as Int,
                                name = data["name"] as String,
                                surname = data["surname"] as String,
                                relativeName = data["relativeName"] as String,
                                address = data["address"] as String,
                                phone = data["phone"] as String,
                                email = data["email"] as String,
                                password = data["password"] as String
                            )
                        } ?: User()
                    } else {
                        continuation.resumeWithException(RuntimeException("User could not be fetched."))
                        return@addOnCompleteListener
                    }
                    continuation.resume(user)
                }
                .addOnFailureListener {
                    throw RuntimeException(it.message)
                }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun addMarker(marker: Marker) {
        val currentUser = firebaseAuth.currentUser
        val markerMap = hashMapOf(
            "id" to marker.id,
            "latitude" to marker.latitude,
            "longitude" to marker.longitude,
            "title" to marker.title,
            "description" to marker.description,
            "type" to marker.type,
            "date" to marker.date,
            "time" to marker.time,
            "userId" to currentUser?.uid.toString()
        )
        firebaseFirestore.collection("markers").document(marker.id)
            .set(markerMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    throw RuntimeException(it.result.toString())
                } else {
                    throw RuntimeException(it.exception)
                }
            }
            .addOnFailureListener {
                throw RuntimeException(it.message)
            }
    }

    override suspend fun getMarkers(): List<Marker> {
        var markers: List<Marker>
        return suspendCoroutine {  continuation ->
            firebaseFirestore.collection("markers")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result
                        markers = documents?.map { document ->
                            document.data.let { data ->
                                Marker(
                                    id = data["id"] as String,
                                    latitude = data["latitude"] as Double,
                                    longitude = data["longitude"] as Double,
                                    title = data["title"] as String,
                                    description = data["description"] as String,
                                    type = data["type"] as String,
                                    date = data["date"] as String,
                                    time = data["time"] as String,
                                    userId = data["userId"] as String
                                )
                            }
                        } ?: emptyList()
                    } else {
                        continuation.resumeWithException(RuntimeException("Markers could not be fetched."))
                        return@addOnCompleteListener
                    }
                    continuation.resume(markers)
                }
                .addOnFailureListener {
                    throw RuntimeException(it.message)
                }
        }
    }
}