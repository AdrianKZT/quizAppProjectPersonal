package com.adrian.quizapppersonalproject.data.repo

import android.util.Log
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.Result
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ResultRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): ResultRepo {

    private fun getDbRef(): CollectionReference {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid?.let {
            db.collection("results")
        }?: throw Exception("No user found")
    }

    override suspend fun addResult(result: Result) {
        getDbRef().document().set(result.toHashMap()).await()
    }

    override suspend fun getResult() = callbackFlow {
        val listener = getDbRef().addSnapshotListener{ value, error ->
            if (error != null) {
                throw error
            }

            val results = mutableListOf<Result>()
            Log.d("debugging", "Hello $results")
            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        results.add(Result.fromHashMap(it))
                    }
                }
                trySend(results)
            }
        }
        awaitClose{
            listener.remove()
        }
    }

}