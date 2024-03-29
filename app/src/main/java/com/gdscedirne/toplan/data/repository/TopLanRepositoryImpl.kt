package com.gdscedirne.toplan.data.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.data.model.News
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import com.gdscedirne.toplan.domain.source.FirebaseSource
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
class TopLanRepositoryImpl @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val generativeModel: GenerativeModel
) : TopLanRepository {

    override fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onNavigate: () -> Unit
    ): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.signInWithEmailAndPassword(email, password, onNavigate)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun signUpWithEmailAndPassword(
        user: User,
        onNavigate: () -> Unit
    ): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.signUpUserWithEmailAndPassword(user, onNavigate)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun saveUser(user: User): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.saveUser(user)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun signOut(): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.signOut()
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun isUserSignedIn(): Flow<ResponseState<Boolean>> {
        return flow {
            emit(ResponseState.Loading)
            val isUserSignedIn = firebaseSource.isUserSignedIn()
            emit(ResponseState.Success(isUserSignedIn))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun updateProfile(user: User): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.updateProfileImage(user)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun uploadImageToStorage(
        uri: Uri,
        context: Context,
        onSuccess: (String, String) -> Unit,
        onFailure: (String) -> Unit,
    ): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.uploadImageToStorage(uri, context, onSuccess, onFailure)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun uploadImageToFirestore(
        imagesUrl: List<String>,
        imageName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.uploadImageToFirestore(imagesUrl, imageName, onSuccess, onFailure)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun getUser(): Flow<ResponseState<User>> {
        return flow {
            emit(ResponseState.Loading)
            val user = firebaseSource.getUser()
            emit(ResponseState.Success(user))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun addMarker(marker: Marker): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.addMarker(marker)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun getMarkers(): Flow<ResponseState<List<Marker>>> {
        return flow {
            emit(ResponseState.Loading)
            val markers = firebaseSource.getMarkers()
            emit(ResponseState.Success(markers))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return currentDate.format(formatter).toString()
    }

    override fun getCurrentTime(): String {
        val currentTime: LocalTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return currentTime.format(formatter)
    }

    // Gemini - Chat
    override fun askQuestion(question: String): Flow<ResponseState<String>> {
        return flow {
            emit(ResponseState.Loading)
            val response = generativeModel.generateContent(question).text.toString()
            emit(ResponseState.Success(response))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun addFeed(feed: Feed): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.addFeed(feed)
            emit(ResponseState.Success(Unit))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun getFeed(): Flow<ResponseState<List<Feed>>> {
        return flow {
            emit(ResponseState.Loading)
            val feed = firebaseSource.getFeed()
            emit(ResponseState.Success(feed))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override fun getNews(): Flow<ResponseState<List<News>>> {
        return flow {
            emit(ResponseState.Loading)
            val news = firebaseSource.getNews()
            emit(ResponseState.Success(news))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

}