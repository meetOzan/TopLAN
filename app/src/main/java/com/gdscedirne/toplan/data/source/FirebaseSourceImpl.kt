package com.gdscedirne.toplan.data.source

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.gdscedirne.toplan.common.uriToBitmap
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.data.model.News
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
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
            "imageUrl" to "  "
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
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("users").document(currentUser?.uid.toString())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        user = document?.data?.let { data ->
                            User(
                                id = data["id"] as String,
                                name = data["name"] as String,
                                surname = data["surname"] as String,
                                relativeName = data["relativeName"] as String,
                                address = data["address"] as String,
                                phone = data["phone"] as String,
                                email = data["email"] as String,
                                password = data["password"] as String,
                                imageUrl = data["imageUrl"] as String
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

    override suspend fun getUserById(userId: String): User {
        var user: User
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("users").document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        user = document?.data?.let { data ->
                            User(
                                id = data["id"] as String,
                                name = data["name"] as String,
                                surname = data["surname"] as String,
                                relativeName = data["relativeName"] as String,
                                address = data["address"] as String,
                                phone = data["phone"] as String,
                                email = data["email"] as String,
                                password = data["password"] as String,
                                imageUrl = data["imageUrl"] as String
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

    override fun updateProfileImage(user: User) {
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
            "imageUrl" to user.imageUrl
        )
        firebaseFirestore.collection("users").document(currentUser?.uid.toString())
            .set(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("TAG", "updateProfileImage: ${it.result}")
                } else {
                    throw RuntimeException(it.exception)
                }
            }
            .addOnFailureListener {
                throw RuntimeException(it.message)
            }
    }

    override fun uploadImageToStorage(
        uri: Uri,
        context: Context,
        onSuccess: (String, String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val contentResolver = context.contentResolver

        val reference = firebaseStorage.reference.child("images").child(imageName)

        val byteArrayOutputStream = ByteArrayOutputStream()
        val mBitmap = uri.uriToBitmap(contentResolver)
        mBitmap?.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()

        reference.putBytes(data).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString(), imageName)
            }.addOnFailureListener { exception ->
                onFailure(exception.message.orEmpty())
            }
        }.addOnFailureListener {
            onFailure(it.message.orEmpty())
        }
    }

    override fun uploadImageToFirestore(
        imagesUrl: List<String>,
        imageName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        val imageMap = hashMapOf(
            "id" to currentUser?.uid.toString(),
            "imagesUrl" to imagesUrl,
            "imageName" to imageName
        )
        firebaseFirestore.collection("images").document(currentUser?.uid.toString())
            .set(imageMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    throw RuntimeException(it.exception)
                }
            }
            .addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
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
            "location" to marker.location,
            "time" to marker.time,
            "userId" to currentUser?.uid.toString(),
            "imageUrl" to marker.imageUrl
        )
        firebaseFirestore.collection("markers").document(marker.id)
            .set(markerMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("TAG", "addMarker: ${it.result}")
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
        return suspendCoroutine { continuation ->
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
                                    location = data["location"] as String,
                                    description = data["description"] as String,
                                    type = data["type"] as String,
                                    date = data["date"] as String,
                                    time = data["time"] as String,
                                    userId = data["userId"] as String,
                                    imageUrl = data["imageUrl"] as String
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

    override suspend fun addFeed(feed: Feed) {
        val currentUser = firebaseAuth.currentUser
        val user = this.getUserById(currentUser?.uid.toString())
        val feedMap = hashMapOf(
            "id" to feed.id,
            "title" to feed.title,
            "imageUrl" to feed.imageUrl,
            "description" to feed.description,
            "user" to user,
            "likeCount" to 0.toLong().toInt(),
            "comments" to emptyList<String>(),
        )
        firebaseFirestore.collection("feeds").document(feed.id)
            .set(feedMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e("TAG", "addFeed: ${it.result}")
                } else {
                    throw RuntimeException(it.exception)
                }
            }
            .addOnFailureListener {
                throw RuntimeException(it.message)
            }
    }

    override suspend fun getFeed(): List<Feed> {
        var feeds: List<Feed>
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("feeds")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result
                        feeds = documents?.map { document ->
                            document.data.let { data ->
                                Feed(
                                    id = data["id"] as String,
                                    title = data["title"] as String,
                                    imageUrl = data["imageUrl"] as String,
                                    user = data["user"] as Map<String, String>,
                                    description = data["description"] as String,
                                    likeCount = data["likeCount"] as Long,
                                    comments = data["comments"] as List<String>,
                                )
                            }
                        } ?: emptyList()
                    } else {
                        continuation.resumeWithException(RuntimeException("Feeds could not be fetched."))
                        return@addOnCompleteListener
                    }
                    continuation.resume(feeds)
                }
                .addOnFailureListener {
                    throw RuntimeException(it.message)
                }
        }
    }

    override suspend fun getNews(): List<News> {
        var news: List<News>
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("news")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result
                        news = documents?.map { document ->
                            document.data.let { data ->
                                News(
                                    id = data["id"] as String,
                                    name = data["name"] as String,
                                    author = data["author"] as String,
                                    imageUrl = data["imageUrl"] as String,
                                    date = data["date"] as String,
                                    isImportant = data["isImportant"] as Boolean,
                                    description = data["description"] as String,
                                    authorImageUrl = data["authorImageUrl"] as String
                                )
                            }
                        } ?: emptyList()
                    } else {
                        continuation.resumeWithException(RuntimeException("News could not be fetched."))
                        return@addOnCompleteListener
                    }
                    continuation.resume(news)
                }
                .addOnFailureListener {
                    throw RuntimeException(it.message)
                }
        }
    }

}