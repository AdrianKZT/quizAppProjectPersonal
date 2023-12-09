package com.adrian.quizapppersonalproject.data.repo

import android.util.Log
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class UserRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): UserRepo {

    private fun getDbRef(): CollectionReference {
        return db.collection("users")
    }

    private fun getUid(): String{
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid ?: throw Exception("No authentic user found")
    }

    override suspend fun getCurrentUser(): User? {
        val snapshot = getDbRef().document(getUid()).get().await()
        return snapshot.data?.let {
            it["id"] = snapshot.id
            User.fromHashMap(it)
        }
    }

    override suspend fun addUser(user: User) {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }

    override suspend fun getUser(id: String): User? {
        val snapshot = getDbRef().document(id).get().await()
        return snapshot.data?.let {
            it["id"] = snapshot.id
            User.fromHashMap(it)
        }
    }

    override suspend fun removeUser() {
        getDbRef().document(getUid()).delete().await()
    }

    override suspend fun updateUser(id: String, user: User) {
        getDbRef().document(id).set(user.toString()).await()
    }

}