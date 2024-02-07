package com.gdscedirne.toplan.data.repository

import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.User
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import com.gdscedirne.toplan.domain.source.FirebaseSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopLanRepositoryImpl @Inject constructor(
    private val firebaseSource: FirebaseSource
) : TopLanRepository {

    override fun signInUserWithEmailAndPassword(
        user: User,
        onNavigate: () -> Unit
    ): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            firebaseSource.signInWithEmailAndPassword(user, onNavigate)
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
}