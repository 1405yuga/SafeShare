package com.example.safeshare.network

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    suspend fun signInWithGoogle(idToken: String): AuthResult? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return firebaseAuth.signInWithCredential(credential).await()
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult? {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun createAccountWithEmailAndPassword(email: String, password: String): AuthResult? {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun getCurrentUser(): FirebaseUser? {
        val user = firebaseAuth.currentUser
        return try {
            user?.reload()?.await()
            firebaseAuth.currentUser
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}